package com.amg.exchange.bankBranchUpload.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.amg.exchange.bankBranchUpload.service.BankBranchUploadService;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.GMailAuthenticator;
import com.amg.exchange.util.SessionStateManage;

@Component("bankBranchUploadBean")
@Scope("session")
public class BankBranchUploadBean {
	
	private BigDecimal countryId;
	private BigDecimal bankBranch;
	private UploadedFile uploadedFile;
	private String totalRecordsRead;
	private String newBranchesCreated;
	private String branchesUpdated;
	private String errorRecordsCount;
	private String errorMessage;
	
	private Boolean uploadScreen = true;
	private Boolean uploadSuccScreen = false;	
	private Boolean uploadUnSuccScreen = false;
	
	private List<CountryMasterDesc> allCountryList = null;
	private List<BankMaster> bankBranchList = null;
	private List<BankBranchUploadExcelList> neftBranchUploadList = null;
	private List<BankBranchUploadExcelList> invalidRecords = null;
	private List<BankBranchUploadExcelList> deletedRecords = null;
	private SessionStateManage session = new SessionStateManage();
	
	@Autowired
	BankBranchUploadService bankBranchUploadService;	
	
	@Autowired(required = true)
	private JavaMailSender mailSender;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	
	
	public void pageNavigation() {
		try {	
			setUploadScreen(true);
			setUploadSuccScreen(false);
			setUploadUnSuccScreen(false);
			clearFields();
			setBankBranchList(null);
			List<CountryMasterDesc> countryList = bankBranchUploadService.getAllCountryList(session.getLanguageId());
			if(countryList!=null && countryList.size() > 0) {
				setAllCountryList(countryList);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("../bankBranchUpload/bankBranchUpload.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void bankLists(){
		List<BankMaster> bankList = bankBranchUploadService.getBankList(getCountryId());		
		if(bankList!=null && bankList.size() > 0){
			setBankBranchList(bankList);
		} else {
			setBankBranch(null);
			setBankBranchList(null);
		}
	}
	
	
	public void uploadToSystem(FileUploadEvent event) {

		try {

			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";
			int lineNoCount = 1;
			List<BankBranchUploadExcelList> neftBranchUploadExcelList = new ArrayList<BankBranchUploadExcelList>();

			InputStream is = uploadedFile.getInputstream();
			byte br1[] = uploadedFile.getContents();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] csvData = line.split(cvsSplitBy);
				BankBranchUploadExcelList neftBranchUploadExcelDt = mappingValuesToListObject(csvData);
				if (neftBranchUploadExcelDt != null) {
					neftBranchUploadExcelList.add(neftBranchUploadExcelDt);
				}
				lineNoCount++;
			}

			// Save Or Update
			if (neftBranchUploadExcelList.size() > 1) {
				HashMap<String, List<BankBranchUploadExcelList>> saveOrUpdate = bankBranchUploadService
						.checkAndSaveOrUpdateBankBranch(getCountryId(),getBankBranch(),
								neftBranchUploadExcelList,
								session.getLanguageId(),session.getUserName());

				List<BankBranchUploadExcelList> deleteListNeftBranchUploadExcel = saveOrUpdate
						.get("DELTEBRANCH");
				List<BankBranchUploadExcelList> inValidListNeftBranchUploadExcel = saveOrUpdate
						.get("INVALIDBRANCH");
				List<BankBranchUploadExcelList> updateListNeftBranchUploadExcel = saveOrUpdate
						.get("UPDATEBRANCH");
				List<BankBranchUploadExcelList> insertListNeftBranchUploadExcel = saveOrUpdate
						.get("INSERTBRANCH");

				int deleteCount = deleteListNeftBranchUploadExcel.size();
				if (deleteCount > 0) {
					setDeletedRecords(deleteListNeftBranchUploadExcel);
				} else {
					setDeletedRecords(null);
				}

				int invalidCount = inValidListNeftBranchUploadExcel.size();
				if (invalidCount > 0) {
					setInvalidRecords(inValidListNeftBranchUploadExcel);
					
				} else {
					setInvalidRecords(null);
				}
				
				List<BankBranchUploadExcelList> errorList = new ArrayList<BankBranchUploadExcelList>();
				if((inValidListNeftBranchUploadExcel!=null && inValidListNeftBranchUploadExcel.size()>0) && (deleteListNeftBranchUploadExcel!=null && deleteListNeftBranchUploadExcel.size()>0)){
					errorList.addAll(inValidListNeftBranchUploadExcel);
					errorList.addAll(deleteListNeftBranchUploadExcel);
					sendEmail(errorList);
				}else if((inValidListNeftBranchUploadExcel!=null && inValidListNeftBranchUploadExcel.size()>0)){
					errorList.addAll(inValidListNeftBranchUploadExcel);
					sendEmail(errorList);
				}else if(deleteListNeftBranchUploadExcel!=null && deleteListNeftBranchUploadExcel.size()>0){
					errorList.addAll(deleteListNeftBranchUploadExcel);
					sendEmail(errorList);
				}
					

				int updateCount = updateListNeftBranchUploadExcel.size();
				int insertCount = insertListNeftBranchUploadExcel.size();

				int totalCount = deleteCount + invalidCount + updateCount
						+ insertCount;

				if (totalCount > 0) {
					if (updateCount > 0 || insertCount > 0) {
						//RequestContext.getCurrentInstance().execute("uploadSuccess.show();");
						setUploadScreen(false);
						setUploadSuccScreen(true);
						setUploadUnSuccScreen(false);
					} else {
						//RequestContext.getCurrentInstance().execute("uploadUnSuccess.show();");
						setUploadScreen(false);
						setUploadSuccScreen(false);
						setUploadUnSuccScreen(true);
					}

					setTotalRecordsRead(String.valueOf(totalCount));
					setNewBranchesCreated(String.valueOf(insertCount));
					setBranchesUpdated(String.valueOf(updateCount));
					setErrorRecordsCount(String.valueOf(invalidCount+deleteCount));

				} else {
					RequestContext.getCurrentInstance().execute(
							"noRecords.show();");
				}
			} else {
				RequestContext.getCurrentInstance()
						.execute("noRecords.show();");
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
	}
	
	
	
	private BankBranchUploadExcelList mappingValuesToListObject(String[] csvData)
	{
		BankBranchUploadExcelList neftBranchUploadExcel =null;
		try{

			if(csvData!=null && csvData.length>0)
			{
				int csvLength = csvData.length;
				neftBranchUploadExcel = new BankBranchUploadExcelList();
				
				//BANK
				if(csvLength > 0 && csvData[0] != null && !csvData[0].equalsIgnoreCase("")){
					String bank = csvData[0];
					bank = bank.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setBank(bank);
				}		
				
				//IFSC CODE
				if(csvLength > 1 && csvData[1] != null && !csvData[1].equalsIgnoreCase("")){
					String ifsc = csvData[1];
					ifsc = ifsc.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setIfsc(ifsc);
				}
				
				//MICR CODE
				if(csvLength > 2 && csvData[2] != null && !csvData[2].equalsIgnoreCase("")){
					String micr = csvData[2];
					micr = micr.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setMicr(micr);
				}
				
				//BRANCH NAME
				if(csvLength > 3 && csvData[3] != null && !csvData[3].equalsIgnoreCase("")){
					String branchName = csvData[3];
					branchName = branchName.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setBranchName(branchName);
					
					if(branchName.length() <= 30){												
						neftBranchUploadExcel.setBranchShortName(branchName);
					}else{
						String shName = branchName.substring(0, 30);
						neftBranchUploadExcel.setBranchShortName(shName);
					}
				}		
				
				//ADDRESS
				StringBuilder sb= new StringBuilder();
				int lastIndex=0;
				for (int i = 4; i < csvLength-4; i++) {
					if(csvLength > 4 && csvData[i] != null && !csvData[i].equalsIgnoreCase("")){
						if(i==csvLength-5)
						{
							sb.append(csvData[i].replace("\"", ""));
						}else
						{
							sb.append(csvData[i].replace("\"", "")+",");
						}						
						lastIndex=i;
					}
				}				
				if (csvLength > 4 && csvData[4] != null && !csvData[4].equalsIgnoreCase("")) {
					neftBranchUploadExcel.setAddress(sb.toString());

					String addr = neftBranchUploadExcel.getAddress();
					if (addr.length() > 0) {

						if(addr.length() > 60) {
							char charSpace = addr.charAt(60);
							if (String.valueOf(charSpace).equalsIgnoreCase(" ")) {
								String str = new String(addr);
								char[] ch1 = new char[60];
								str.getChars(0, 60, ch1, 0);
								String addr1 = String.valueOf(ch1).trim().replaceAll(" +", " ");
								String addr2 = addr.substring(61, addr.length());

								neftBranchUploadExcel.setAddr1(addr1);
								neftBranchUploadExcel.setAddr2(addr2.trim().replaceAll(" +", " "));

							} else {
								String check = addr.substring(0,59);
								String sp[] = check.split(" ");
								if (sp.length > 0) {
									StringBuilder sb1 = new StringBuilder();
									for (int i = 0; i < sp.length - 1; i++) {
										if(i==sp.length -2)
										{
											sb1.append(sp[i]);
										}else
										{
											sb1.append(sp[i]+" ");
										}
										
									}
									String addr1 = sb1.toString().trim().replaceAll(" +", " ");
									String addr2 = addr.substring(59,addr.length());

									String last = sp[sp.length - 1];
									String finalAddr2 = last + addr2;

									neftBranchUploadExcel.setAddr1(addr1);
									neftBranchUploadExcel.setAddr2(finalAddr2.trim().replaceAll(" +", " "));
								} else {
									neftBranchUploadExcel.setAddr1(addr.trim().replaceAll(" +", " "));
								}
							}
						} else {
							neftBranchUploadExcel.setAddr1(addr.trim().replaceAll(" +", " "));
						}
					}
				}		
				
				//CONTACT
				if(csvLength > 5 && csvData[lastIndex+1] != null && !csvData[lastIndex+1].equalsIgnoreCase("")){
					String contact = csvData[lastIndex+1];
					contact = contact.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setContact(contact);
				}
				
				//CITY
				if(csvLength > 6 && csvData[lastIndex+2] != null && !csvData[lastIndex+2].equalsIgnoreCase("")){
					String city = csvData[lastIndex+2];
					city = city.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setCity(city);
				}
				
				//DISTRICT
				if(csvLength > 7 && csvData[lastIndex+3] != null && !csvData[lastIndex+3].equalsIgnoreCase("")){
					String district = csvData[lastIndex+3];
					district = district.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setDistrict(district);
				}
				
				//STATE
				if(csvLength > 8 && csvData[lastIndex+4] != null && !csvData[lastIndex+4].equalsIgnoreCase("")){
					String state = csvData[lastIndex+4];
					state = state.trim().replaceAll(" +", " ");
					neftBranchUploadExcel.setState(state);
				}
			}
		}catch(Exception e)
		{
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		return neftBranchUploadExcel;
	}	
	
	
	public void uploadList(FileUploadEvent event) {
		
			try {		
				
				if(getCountryId()==null){
					RequestContext.getCurrentInstance().execute("selectCountry.show();");
				} else if(getBankBranch()==null){
					RequestContext.getCurrentInstance().execute("selectBank.show();");
				} else {
					setNeftBranchUploadList(null);
					setTotalRecordsRead(null);
					setNewBranchesCreated(null);
					setBranchesUpdated(null);
					setErrorRecordsCount(null);
					
					uploadedFile = event.getFile();
					uploadedFile.getFileName();
					
					if(uploadedFile.getFileName().endsWith(".csv")){
						uploadToSystem(event);
					} else {
						Workbook workbook = null;
						
						if(uploadedFile.getFileName().endsWith(".xls")) {
							workbook = new HSSFWorkbook(uploadedFile.getInputstream());
						} else if(uploadedFile.getFileName().endsWith(".xlsx")) {
							workbook = new XSSFWorkbook(uploadedFile.getInputstream());
						}
						
						int numberOfSheets = workbook.getNumberOfSheets();
						List<BankBranchUploadExcelList> neftBranchUploadExcelList = new ArrayList<BankBranchUploadExcelList>();
						for (int i = 0; i < numberOfSheets; i++) {
							Sheet sheet = workbook.getSheetAt(i);
							Iterator<Row> rowIterator = sheet.iterator();
							int rowNumber = 1;
							
							while (rowIterator.hasNext()) {
								String bank =null;
								Row row = rowIterator.next();
								if (rowNumber == 1) {
									rowNumber++;
									continue;
								}

								Iterator<Cell> cellIterator = row.cellIterator();
								BankBranchUploadExcelList bankBranchUploadExcelList = new BankBranchUploadExcelList();
								
								while (cellIterator.hasNext()) {
									Cell cell = cellIterator.next();

									// BANK
									
									if (cell.getColumnIndex() == 0) {
										bank = cell.getStringCellValue().trim().replaceAll(" +", " ");
										bankBranchUploadExcelList.setBank(bank);
									}
									if(bank!=null)
									{
										// IFSC CODE
										if (cell.getColumnIndex() == 1) {
											String ifscCode = cell.getStringCellValue().trim().replaceAll(" +", " ");
											bankBranchUploadExcelList.setIfsc(ifscCode);
										}

										// MICR CODE
										if (cell.getColumnIndex() == 2) {
											try{
												Double micrCode = cell.getNumericCellValue();
												if(micrCode < 1){
													bankBranchUploadExcelList.setMicr(null);
												}else{
													bankBranchUploadExcelList.setMicr(new BigDecimal(micrCode.longValue()).toPlainString().trim().replaceAll(" +", " "));
												}												
											}catch(Exception e)
											{
												bankBranchUploadExcelList.setMicr(null);
											}
											
										}

										// BRANCH NAME
										if (cell.getColumnIndex() == 3) {
											String branchName = cell.getStringCellValue().trim().replaceAll(" +", " ");
											bankBranchUploadExcelList.setBranchName(branchName);
											
											if(branchName.length() < 30){												
												bankBranchUploadExcelList.setBranchShortName(branchName);
											}else{
												String shName = branchName.substring(0, 29);
												bankBranchUploadExcelList.setBranchShortName(shName);
											}										
										}

										// ADDRESS
										if (cell.getColumnIndex() == 4) {
											String address = cell.getStringCellValue().trim();
											bankBranchUploadExcelList.setAddress(address);

											String addr = bankBranchUploadExcelList.getAddress();
											if (addr.length() > 0) {

												if (addr.length() > 60) {
													char charSpace = addr.charAt(60);
													if (String.valueOf(charSpace).equalsIgnoreCase(" ")) {
														String str = new String(addr);
														char[] ch1 = new char[60];
														str.getChars(0, 60, ch1, 0);
														String addr1 = String.valueOf(ch1);
														String addr2 = addr.substring(61,addr.length());

														bankBranchUploadExcelList.setAddr1(addr1.trim().replaceAll(" +", " "));
														bankBranchUploadExcelList.setAddr2(addr2.trim().replaceAll(" +", " "));

													} else {
														String check = addr.substring(0, 59);
														String sp[] = check.split(" ");
														if (sp.length > 0) {
															StringBuilder sb1 = new StringBuilder();
															for (int x = 0; x < sp.length - 1; x++) {
																if (x == sp.length - 2) {
																	sb1.append(sp[x]);
																} else {
																	sb1.append(sp[x] + " ");
																}

															}
															String addr1 = sb1.toString();
															String addr2 = addr.substring(59, addr.length());

															String last = sp[sp.length - 1];
															String finalAddr2 = last + addr2;

															bankBranchUploadExcelList.setAddr1(addr1.trim().replaceAll(" +", " "));
															bankBranchUploadExcelList.setAddr2(finalAddr2.trim().replaceAll(" +", " "));
														} else {
															bankBranchUploadExcelList.setAddr1(addr.trim().replaceAll(" +", " "));
														}
													}
												} else {
													bankBranchUploadExcelList.setAddr1(addr.trim().replaceAll(" +", " "));
												}
											}
										}

										// CONTACT
										if (cell.getColumnIndex() == 5) {										
											try{
												Double contact = cell.getNumericCellValue();
												bankBranchUploadExcelList.setContact(new BigDecimal(contact.longValue()).toPlainString().trim().replaceAll(" +", " "));
											}catch(Exception e)
											{
												bankBranchUploadExcelList.setContact(null);
											}
										}

										// CITY
										if (cell.getColumnIndex() == 6) {
											String city = cell.getStringCellValue().trim().replaceAll(" +", " ");
											bankBranchUploadExcelList.setCity(city);
										}

										// DISTRICT
										if (cell.getColumnIndex() == 7) {
											String district = cell.getStringCellValue().trim().replaceAll(" +", " ");
											bankBranchUploadExcelList.setDistrict(district);
										}

										// STATE
										if (cell.getColumnIndex() == 8) {
											String state = cell.getStringCellValue().trim().replaceAll(" +", " ");
											bankBranchUploadExcelList.setState(state);
										}										
									}
									

								}
								neftBranchUploadExcelList.add(bankBranchUploadExcelList);
							}							
						}
						// Save Or Update
						if (neftBranchUploadExcelList.size() > 1) {
							if(neftBranchUploadExcelList.size() > 5000){
								RequestContext.getCurrentInstance().execute("recordsNotExceed.show();");
							}else{
								HashMap<String, List<BankBranchUploadExcelList>> saveOrUpdate = bankBranchUploadService.checkAndSaveOrUpdateBankBranch(getCountryId(),getBankBranch(), neftBranchUploadExcelList,session.getLanguageId(),session.getUserName());

								List<BankBranchUploadExcelList> deleteListNeftBranchUploadExcel = saveOrUpdate.get("DELTEBRANCH");
								List<BankBranchUploadExcelList> inValidListNeftBranchUploadExcel = saveOrUpdate.get("INVALIDBRANCH");
								List<BankBranchUploadExcelList> updateListNeftBranchUploadExcel = saveOrUpdate.get("UPDATEBRANCH");
								List<BankBranchUploadExcelList> insertListNeftBranchUploadExcel = saveOrUpdate.get("INSERTBRANCH");

								int deleteCount = deleteListNeftBranchUploadExcel.size();
								if (deleteCount > 0) {
									setDeletedRecords(deleteListNeftBranchUploadExcel);
								} else {
									setDeletedRecords(null);
								}

								int invalidCount = inValidListNeftBranchUploadExcel.size();
								if (invalidCount > 0) {
									setInvalidRecords(inValidListNeftBranchUploadExcel);									
								} else {
									setInvalidRecords(null);
								}

								List<BankBranchUploadExcelList> errorList = new ArrayList<BankBranchUploadExcelList>();
								if((inValidListNeftBranchUploadExcel!=null && inValidListNeftBranchUploadExcel.size()>0) && (deleteListNeftBranchUploadExcel!=null && deleteListNeftBranchUploadExcel.size()>0)){
									errorList.addAll(inValidListNeftBranchUploadExcel);
									errorList.addAll(deleteListNeftBranchUploadExcel);
									sendEmail(errorList);
								}else if((inValidListNeftBranchUploadExcel!=null && inValidListNeftBranchUploadExcel.size()>0)){
									errorList.addAll(inValidListNeftBranchUploadExcel);
									sendEmail(errorList);
								}else if(deleteListNeftBranchUploadExcel!=null && deleteListNeftBranchUploadExcel.size()>0){
									errorList.addAll(deleteListNeftBranchUploadExcel);
									sendEmail(errorList);
								}
								
								int updateCount = updateListNeftBranchUploadExcel.size();
								int insertCount = insertListNeftBranchUploadExcel.size();

								int totalCount = deleteCount + invalidCount + updateCount + insertCount;

								if (totalCount > 0) {
									if (updateCount > 0 || insertCount > 0) {
										//RequestContext.getCurrentInstance().execute("uploadSuccess.show();");
										setUploadScreen(false);
										setUploadSuccScreen(true);
										setUploadUnSuccScreen(false);
									} else {
										//RequestContext.getCurrentInstance().execute("uploadUnSuccess.show();");
										setUploadScreen(false);
										setUploadSuccScreen(false);
										setUploadUnSuccScreen(true);
									}

									setTotalRecordsRead(String.valueOf(totalCount));
									setNewBranchesCreated(String.valueOf(insertCount));
									setBranchesUpdated(String.valueOf(updateCount));
									setErrorRecordsCount(String.valueOf(invalidCount+deleteCount));

								} else {
									RequestContext.getCurrentInstance().execute("noRecords.show();");
								}
							}
							
						} else {
							RequestContext.getCurrentInstance().execute("noRecords.show();");
						}
					}				
				}
				
			} catch (Exception e) {
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("errorPage.show();");
			}
		}	
	
	public void sendEmail(List<BankBranchUploadExcelList> errorList)
	{
		String subject = "Invalid Bank Branch List";
		List<ApplicationSetup> lstApplicationSetup;
		try {
			lstApplicationSetup = iPersonalRemittanceService.getEmailFromAppSetup(session.getCompanyId(), session.getCountryId());
			emailToErrorRecords(lstApplicationSetup,errorList, session.getEmail(), subject);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("errorPage.show();");
		}
		

	}
	
	public void emailToErrorRecords(List<ApplicationSetup> lstApplicationSetup,List<BankBranchUploadExcelList> errorList,String email ,String subject) {
		try {
						
			ApplicationSetup applicationSetup =lstApplicationSetup.get(0);

			String host = applicationSetup.getEmailHost();
			final String user =applicationSetup.getEmailUserName();
			final String pass = applicationSetup.getEmailPassword();
			String from=applicationSetup.getEmailId();
			BigDecimal port=applicationSetup.getEmailPortNo();

			Properties props= new Properties();

			props.put("mail.smtp.host", host); 
			props.setProperty("mail.smtp.auth","true");
			//props.put("mail.smtp.port", port);

			Session session = Session.getInstance(props,new GMailAuthenticator(user, pass));
			session.setPasswordAuthentication(new URLName("smtp",host,port.intValue(),"INBOX",user,pass), new PasswordAuthentication(user,pass));
			
			InternetAddress arReplTo[] = new InternetAddress[1];

			Message message = new MimeMessage(session); // Define message
			// Fill its headers
			message.setSubject(subject);
			message.setFrom(new InternetAddress("Al Mulla Exchange Online <"+from+">"));
			arReplTo[0] = new InternetAddress(from);
			message.setReplyTo(arReplTo);

			InternetAddress[] address = {new InternetAddress(email)};
			message.setRecipients(Message.RecipientType.TO, address);

			String text = errorRecordsList(errorList);

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(text, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			
			
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}
	
	
	/*private void sendMail(List<BankBranchUploadExcelList> errorList) {

		try {
			final MimeMessage mimeMessage = mailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo("abc@gmail.com");
			message.setSubject("Invalid Records");

			message.setText(errorRecordsList(errorList));
			//mailSender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println("Mail not sent to user :: " + e);
		}
	}*/
	
	private String errorRecordsList(List<BankBranchUploadExcelList> errorList){
		StringBuffer content = new StringBuffer();
		StringBuffer contentIFSC = new StringBuffer();
		StringBuffer contentMicr = new StringBuffer();
		StringBuffer contentBranch = new StringBuffer();
		StringBuffer contentAddr = new StringBuffer();
		StringBuffer contentContact = new StringBuffer();
		StringBuffer contentCity = new StringBuffer();
		StringBuffer contentState = new StringBuffer();
		StringBuffer contentDistrict = new StringBuffer();
		StringBuffer contentRecordInactive = new StringBuffer();
		StringBuffer contentRecordDeleted = new StringBuffer();
		StringBuffer contentInvalidFormat = new StringBuffer();
		
		content.append("<!DOCTYPE html>")
	       .append("<html>")
	       .append("<head>")
	       .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
	       .append("<title>Al Mulla Exchange</title>")
	       .append("</head>")
	       .append("<body bgcolor=\"#ededed\">")	       
	       .append("<hr>")
	       .append("<h1>Invalid Bank Branch List</h1>")
	       .append("<table cellspacing=0 border=1>")
		   .append("<tbody>")
		   .append("<tr>")
		   .append("<th><nobr>BANK</nobr></th>")
		   .append("<th><nobr>BANK BRANCH ID</nobr></th>")
		   .append("<th><nobr>IFSC</nobr></th>")		       
		   .append("<th><nobr>MICR</nobr></th>")
		   .append("<th><nobr>BRANCH</nobr></th>")
		   .append("<th><nobr>CITY</nobr></th>")
		   .append("<th><nobr>DISTRICT</nobr></th>")
		   .append("<th><nobr>STATE</nobr></th>")
		   .append("<th><nobr>REMARKS</nobr></th>");
		
		for(BankBranchUploadExcelList lst : errorList) {			
			
			if(lst.getColumnInvalid()!=null && lst.getColumnInvalid().equalsIgnoreCase("IFSCINVALID"))
			{
				contentIFSC.append("<tr>");
				contentIFSC.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentIFSC.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentIFSC.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentIFSC.append("</tr>");
			}
			
			if(lst.getColumnInvalid()!=null && lst.getColumnInvalid().equalsIgnoreCase("MICRINVALID"))
			{
				contentMicr.append("<tr>");
				contentMicr.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentMicr.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentMicr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentMicr.append("</tr>");
			}
			
			if(lst.getColumnInvalid()!=null && lst.getColumnInvalid().equalsIgnoreCase("BRANCHINVALID"))
			{
				contentBranch.append("<tr>");
				contentBranch.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentBranch.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentBranch.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentBranch.append("</tr>");
			}
			
			if(lst.getColumnInvalid()!=null && lst.getColumnInvalid().equalsIgnoreCase("ADDRINVALID"))
			{
				contentAddr.append("<tr>");
				contentAddr.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentAddr.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentAddr.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentAddr.append("</tr>");
			}
			
			if(lst.getColumnInvalid()!=null && lst.getColumnInvalid().equalsIgnoreCase("CONTACTINVALID"))
			{
				contentContact.append("<tr>");
				contentContact.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentContact.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentContact.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentContact.append("</tr>");
			}
			
			if(lst.getColumnInvalid().equalsIgnoreCase("CITYINVALID"))
				{
					contentCity.append("<tr>");
					contentCity.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
			    	   
			    	   if(lst.getBankBranchId()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getIfsc()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getMicr()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getBranchName()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getCity()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getDistrict()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getState()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getState()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   
			    	   if(lst.getRemarks()!=null){
			    		   contentCity.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
			    	   }else{
			    		   contentCity.append("<td><nobr>"+" "+"</nobr></td>");
			    	   }
			    	   contentCity.append("</tr>");
				}	
			
			if(lst.getColumnInvalid().equalsIgnoreCase("STATEINVALID"))
			{
				contentState.append("<tr>");
				contentState.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentState.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentState.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentState.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentState.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentState.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentState.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentState.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentState.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentState.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentState.append("</tr>");
			}
			
			if(lst.getColumnInvalid().equalsIgnoreCase("DISTRICTINVALID"))
			{
				contentDistrict.append("<tr>");
				contentDistrict.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentDistrict.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentDistrict.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentDistrict.append("</tr>");
			}
			
			if(lst.getColumnInvalid().equalsIgnoreCase("RECORDDELETED"))
			{
				contentRecordDeleted.append("<tr>");
				contentRecordDeleted.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentRecordDeleted.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentRecordDeleted.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentRecordDeleted.append("</tr>");
			}
			
			if(lst.getColumnInvalid().equalsIgnoreCase("RECORDINVALID"))
			{
				contentRecordInactive.append("<tr>");
				contentRecordInactive.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentRecordInactive.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentRecordInactive.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentRecordInactive.append("</tr>");
			}
			
			if(lst.getColumnInvalid().equalsIgnoreCase("INVALIDFORMAT"))
			{
				contentInvalidFormat.append("<tr>");
				contentInvalidFormat.append("<td><nobr>"+lst.getBank()+"</nobr></td>");
		    	   
		    	   if(lst.getBankBranchId()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getBankBranchId()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getIfsc()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getIfsc()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getMicr()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getMicr()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getBranchName()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getBranchName()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getCity()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getCity()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getDistrict()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getDistrict()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getState()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getState()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   
		    	   if(lst.getRemarks()!=null){
		    		   contentInvalidFormat.append("<td><nobr>"+lst.getRemarks()+"</nobr></td>");
		    	   }else{
		    		   contentInvalidFormat.append("<td><nobr>"+" "+"</nobr></td>");
		    	   }
		    	   contentInvalidFormat.append("</tr>");
			}
			
	       }
		content.append(contentIFSC.toString());
		content.append(contentMicr.toString());
		content.append(contentBranch.toString());
		content.append(contentAddr.toString());
		content.append(contentContact.toString());
		content.append(contentCity.toString());
		content.append(contentState.toString());
		content.append(contentDistrict.toString());
		content.append(contentRecordDeleted.toString());
		content.append(contentRecordInactive.toString());
		content.append(contentInvalidFormat.toString());
		content.append("</tbody>")
		       .append("</table>")
		       .append("<hr>")
		       .append("</body>")
		       .append("</html>");
		
		System.out.println(content.toString());
		return content.toString();
	}
	
	
	private void clearFields() {
		setCountryId(null);
		setBankBranch(null);
		setTotalRecordsRead(null);
		setNewBranchesCreated(null);
		setBranchesUpdated(null);
		setErrorRecordsCount(null);
	}	
	
	public void uploadScreen(){
		setUploadScreen(true);
		setUploadSuccScreen(false);
		setUploadUnSuccScreen(false);
		setBankBranch(null);
		setTotalRecordsRead(null);
		setNewBranchesCreated(null);
		setBranchesUpdated(null);
		setErrorRecordsCount(null);
	}
	
	
	public void exit() {
		try {	
			setUploadScreen(true);
			setUploadSuccScreen(false);
			setUploadUnSuccScreen(false);
			setTotalRecordsRead(null);
			setNewBranchesCreated(null);
			setBranchesUpdated(null);
			setErrorRecordsCount(null);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//Getters and Setters

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public BankBranchUploadService getNeftBranchUploadService() {
		return bankBranchUploadService;
	}

	public void setNeftBranchUploadService(
			BankBranchUploadService bankBranchUploadService) {
		this.bankBranchUploadService = bankBranchUploadService;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public List<BankMaster> getBankBranchList() {
		return bankBranchList;
	}

	public void setBankBranchList(List<BankMaster> bankBranchList) {
		this.bankBranchList = bankBranchList;
	}

	public List<CountryMasterDesc> getAllCountryList() {
		return allCountryList;
	}

	public void setAllCountryList(List<CountryMasterDesc> allCountryList) {
		this.allCountryList = allCountryList;
	}

	public BigDecimal getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BigDecimal bankBranch) {
		this.bankBranch = bankBranch;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	public String getTotalRecordsRead() {
		return totalRecordsRead;
	}

	public void setTotalRecordsRead(String totalRecordsRead) {
		this.totalRecordsRead = totalRecordsRead;
	}

	public String getNewBranchesCreated() {
		return newBranchesCreated;
	}

	public void setNewBranchesCreated(String newBranchesCreated) {
		this.newBranchesCreated = newBranchesCreated;
	}

	public String getBranchesUpdated() {
		return branchesUpdated;
	}

	public void setBranchesUpdated(String branchesUpdated) {
		this.branchesUpdated = branchesUpdated;
	}

	public String getErrorRecordsCount() {
		return errorRecordsCount;
	}

	public void setErrorRecordsCount(String errorRecordsCount) {
		this.errorRecordsCount = errorRecordsCount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<BankBranchUploadExcelList> getNeftBranchUploadList() {
		return neftBranchUploadList;
	}

	public void setNeftBranchUploadList(
			List<BankBranchUploadExcelList> neftBranchUploadList) {
		this.neftBranchUploadList = neftBranchUploadList;
	}

	public List<BankBranchUploadExcelList> getInvalidRecords() {
		return invalidRecords;
	}

	public void setInvalidRecords(List<BankBranchUploadExcelList> invalidRecords) {
		this.invalidRecords = invalidRecords;
	}

	public List<BankBranchUploadExcelList> getDeletedRecords() {
		return deletedRecords;
	}

	public void setDeletedRecords(List<BankBranchUploadExcelList> deletedRecords) {
		this.deletedRecords = deletedRecords;
	}

	public BankBranchUploadService getBankBranchUploadService() {
		return bankBranchUploadService;
	}

	public void setBankBranchUploadService(
			BankBranchUploadService bankBranchUploadService) {
		this.bankBranchUploadService = bankBranchUploadService;
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	public Boolean getUploadScreen() {
		return uploadScreen;
	}


	public void setUploadScreen(Boolean uploadScreen) {
		this.uploadScreen = uploadScreen;
	}


	public Boolean getUploadSuccScreen() {
		return uploadSuccScreen;
	}


	public void setUploadSuccScreen(Boolean uploadSuccScreen) {
		this.uploadSuccScreen = uploadSuccScreen;
	}


	public Boolean getUploadUnSuccScreen() {
		return uploadUnSuccScreen;
	}


	public void setUploadUnSuccScreen(Boolean uploadUnSuccScreen) {
		this.uploadUnSuccScreen = uploadUnSuccScreen;
	}


	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}


	public void setiPersonalRemittanceService(
			IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}
	
}
