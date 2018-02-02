package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("countrymasterupload")
@Scope("session")
public class CountryMasterUploadBean extends CountryMasterBean implements Serializable {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(CountryMasterUploadBean.class);
	private UploadedFile uploadedFile;
	private String fileContent;
	private String errorMessage;
	
	private Boolean booFileUploaded ;
	
	
	private SessionStateManage sess = new SessionStateManage();
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBooFileUploaded() {
		return booFileUploaded;
	}

	public void setBooFileUploaded(Boolean booFileUploaded) {
		this.booFileUploaded = booFileUploaded;
	}

	public List<CountryMasterDataTable> getUploadrDataTableList() {
		return uploadrDataTableList;
	}

	public void setUploadrDataTableList(List<CountryMasterDataTable> uploadrDataTableList) {
		this.uploadrDataTableList = uploadrDataTableList;
	}


	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void exportExceltoDataTable(FileUploadEvent event) throws IOException, SerialException, SQLException {
		uploadedFile = event.getFile();
		uploadedFile.getFileName();
		uploadrDataTableList.clear();
		LOGGER.info("File Name " + uploadedFile.getFileName());
		LOGGER.info("File Size " + uploadedFile.getSize());
		LOGGER.info("Exit into handleFileUpload method");
		
		setBooFileUploaded(true);
	}

	public void uploadtoDatatable() throws IOException {
		
		// FileInputStream fileInputStream = new
		// FileInputStream(uploadedFile.getInputstream());
		
		
		if(getBooFileUploaded()==null)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Please upload the file ");
		}
		else if(getBooFileUploaded()==false)
		{
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Already file is uploaded . ");
		}
		else {
		boolean success = true;
		
		Iterator<Row> rowIterator  = null;
		
		
		LOGGER.info("uploadedFile" + uploadedFile==null);
		LOGGER.info("uploadedFile" + uploadedFile.getFileName());
		
		if(uploadedFile.getFileName().contains(".xlsx"))
		{
			XSSFWorkbook workbook = null;
			workbook = new XSSFWorkbook(uploadedFile.getInputstream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
		}
		else
		{
			HSSFWorkbook workbook = null;
			try {
				workbook = new HSSFWorkbook(uploadedFile.getInputstream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			HSSFSheet sheet = workbook.getSheetAt(0);
			rowIterator = sheet.iterator();
		}
		
		
		try {
			String readingInput = null;
			CountryMasterDataTable dataTable = null;
			
			int previousCell;
			int currentCell;
			/*int currentRowNumber;*/
			
			int rowNumber =1;
			while (rowIterator.hasNext()) {
				
				previousCell = -1;
			    currentCell = 0;
				
				LOGGER.info("Row NUmber" + rowNumber);
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				dataTable = new CountryMasterDataTable();
				int count = 0;
				if(rowNumber==1)
				{
					rowNumber++;
					continue;
				}
				try {
					while (cellIterator.hasNext()) {
						if (count >= 11) {
							break;
						}
						
					
						count++;
						Cell cell = cellIterator.next();
						currentCell = cell.getColumnIndex();
						
						
						 if (previousCell == currentCell-1)  {
					            //...
					        }
					        else {
					        	throw new Exception(rowNumber + "Row number "+ count + " Cell is empty "  );
					        }
						 previousCell = currentCell;
						
						/*if(cell==null ||  cell.getCellType() == Cell.CELL_TYPE_BLANK)
						{
							
						}*/
						// Check the cell type and format accordingly
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							// System.out.print(cell.getNumericCellValue() +
							// "\t");
							Double d = cell.getNumericCellValue();
							int i = d.intValue();
							readingInput = String.valueOf(i);
							break;
						case Cell.CELL_TYPE_STRING:
							// System.out.print(cell.getStringCellValue() +
							// "\t");
							readingInput = cell.getStringCellValue().trim();
							break;
						case Cell.CELL_TYPE_BLANK:
							throw new Exception(count + "Cell is empty " + rowNumber );
							
							
						}
						LOGGER.info(count);
						LOGGER.info(readingInput);
						LOGGER.info(readingInput.length());
						
					
						
						if (count == 1) {
							
							if (readingInput.length() != 3) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Country Code Length sohould be Three . Row number is " + rowNumber );
							}
							
							CountryMaster cm= iCountryMasterservice.viewByCode(readingInput);
							if(cm!=null && cm.getCountryId()!=null)
							{
								dataTable.setDuplicate("Duplicate");
								dataTable.setCountryId(cm.getCountryId());
								dataTable.setModifiedBy(sess.getUserName());
								dataTable.setModifiedDate(new Date());
								dataTable.setApprovedBy(null);
								dataTable.setApprovedDate(null);
								dataTable.setRemarks(null);
								dataTable.setIsActive(Constants.U);
							/*	dataTable.setCreatedBy(cm.getCreatedBy());
								dataTable.setCreatedDate(cm.getCreatedDate());*/
								List<CountryMasterDesc> list = iCountryMasterservice.viewDescriptionsById(cm.getCountryId());
								
								for (CountryMasterDesc countryMasterDesc : list) {
									
									if (countryMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
										
										dataTable.setEcountryDescId(countryMasterDesc.getCountryMasterId());
										dataTable.setElangid(countryMasterDesc.getFsLanguageType().getLanguageId());
									}
									if (countryMasterDesc.getFsLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
										dataTable.setLcountryDescId(countryMasterDesc.getCountryMasterId());
										dataTable.setLlangid(countryMasterDesc.getFsLanguageType().getLanguageId());
									}
								}
							}
							else
							{
								dataTable.setDuplicate("New Record");
								dataTable.setElangid(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID ));
								dataTable.setLlangid(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
								dataTable.setCreatedBy(sess.getUserName());
								dataTable.setCreatedDate(new Date());
								dataTable.setApprovedBy(null);
								dataTable.setApprovedDate(null);
								dataTable.setRemarks(null);
								dataTable.setIsActive(Constants.U);
								
							}
							
							dataTable.setCountryCode(readingInput);
						} else if (count == 2) {
							if (readingInput.length() != 2) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Alpha2 Code sohould be two . Row number is " + rowNumber );
							}
							dataTable.setCountryAlpha2Code(readingInput);
						} else if (count == 3) {
							if (readingInput.length() != 3) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Alpha3 Code sohould be three . Row number is " + rowNumber );
							}
							dataTable.setCountryAlpha3Code(readingInput);
						} else if (count == 4) {
							if (readingInput.length()== 0 ||  readingInput.length() >= 15) {
								success = false;
								setUploadedFile(null);
								throw new Exception("ISO code sohould be less than or equal to  15 . Row number is " + rowNumber );
							}
							dataTable.setIsoCode(readingInput);
						} else if (count == 5) {
							if (readingInput.length()== 0 || readingInput.length() >= 8) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Country telephone code sohould be less than or equal to  8 . Row number is " + rowNumber );
							}
							dataTable.setTelCode(readingInput);
						} else if (count == 6) {
							if (readingInput.length() != 1) {
								success = false;
								setUploadedFile(null);
								throw new Exception("State status sohould be 1 . Row number is " + rowNumber );
							}
							dataTable.setStateStatus(readingInput);
						} else if (count == 7) {
							if (readingInput.length() != 1) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Business Country sohould be 1 . Row number is " + rowNumber );
							}
							dataTable.setBusinessCountry(readingInput);
						} else if (count == 8) {
							if (readingInput.length()== 0 || readingInput.length() >=39) {
								success = false;
								setUploadedFile(null);
								throw new Exception("English country name sohould be less than or equal to  40. Row number is " + rowNumber );
							}
							dataTable.setEnglishDescption(readingInput);
						} else if (count == 9) {
							if (readingInput.length()== 0 || readingInput.length() >=39) {
								success = false;setUploadedFile(null);
								throw new Exception("Local country name sohould be less than or equal to  40. Row number is " + rowNumber );
							}
							dataTable.setLocalDescption(readingInput);
						} else if (count == 10) {
							if (readingInput.length()== 0 || readingInput.length() >=39) {
								success = false;
								setUploadedFile(null);
								throw new Exception("English nationality sohould be less than or equal to  40. Row number is " + rowNumber );
							}
							dataTable.setEnglishNationality(readingInput);
						} else if (count == 11) {
							if (readingInput.length()== 0 || readingInput.length() >=39) {
								success = false;
								setUploadedFile(null);
								throw new Exception("Local nationality sohould be less than or equal to  40. Row number is " + rowNumber );
							}
							dataTable.setLocalNationality(readingInput);
						}
					}
					uploadrDataTableList.add(dataTable);
					rowNumber++;
					dataTable = null;
					System.out.println("");
				} catch (Exception e) {
					success = false;
					setUploadedFile(null);
					LOGGER.info("Exception-------------------------------->"+e.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
					setErrorMessage(e.getMessage());
					break;
				}
			}
		} catch (Exception e1) {
			success = false;
			setUploadedFile(null);
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage(e1.getMessage());
		}
		if(success) {
			setBooDtEnabled(true);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterUpload.xhtml");
			setUploadedFile(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		setBooFileUploaded(false);
		}
	}
	
	
	
	
	
	public void save() {
		LOGGER.info("Entering into saveAll method");
		CountryMaster master = null;
		CountryMasterDesc desc = null;
		HashMap<String, Object> saveObject = new HashMap<String, Object>();
		List<CountryMaster> saveMasterList = new CopyOnWriteArrayList<CountryMaster>();
		List<CountryMasterDesc> saveDescList = new CopyOnWriteArrayList<CountryMasterDesc>();
		List<CountryMasterDataTable> saveDescListdt = uploadrDataTableList;
		for (CountryMasterDataTable dataTable : saveDescListdt) {
			master = null;
			desc = null;
			master = new CountryMaster();
			desc = new CountryMasterDesc();
			LOGGER.info("Input values *************************************************");
			LOGGER.info(dataTable);
			LOGGER.info("Input values *************************************************");
			master.setCountryId(dataTable.getCountryId());
			master.setCountryCode(dataTable.getCountryCode());
			master.setCountryAlpha2Code(dataTable.getCountryAlpha2Code());
			master.setCountryAlpha3Code(dataTable.getCountryAlpha3Code());
			master.setBusinessCountry(dataTable.getBusinessCountry());
			/*master.setStateStatus(dataTable.getStateStatus());*/
			master.setCountryIsoCode(dataTable.getIsoCode());
			master.setCountryTelCode(dataTable.getTelCode());
			/*master.setRemarks(dataTable.getRemarks());
			master.setCreatedBy(dataTable.getCreatedBy());
			master.setCreatedDate(dataTable.getCreatedDate());
			master.setModifiedBy(dataTable.getModifiedBy());
			master.setModifiedDate(dataTable.getModifiedDate());
			master.setApprovedBy(dataTable.getApprovedBy());
			master.setApprovedDate(dataTable.getApprovedDate());
			master.setIsActive(dataTable.getIsActive());*/
			master.setCountryActive(dataTable.getIsActive());
			
				saveMasterList.add(master);
				for (int i = 0; i < 2; i++) {
					desc = null;
					desc = new CountryMasterDesc();
					desc.setFsCountryMaster(master);
					if (i == 0) {
						desc.setCountryName(dataTable.getEnglishDescption());
						desc.setNationality(dataTable.getEnglishNationality());
						desc.setCountryMasterId(dataTable.getEcountryDescId());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getElangid());
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getEnglishNationality());
					}
					if (i == 1) {
						desc.setCountryName(dataTable.getLocalDescption());
						desc.setNationality(dataTable.getLocalNationality());
						desc.setCountryMasterId(dataTable.getLcountryDescId());
						LanguageType ltype = new LanguageType();
						ltype.setLanguageId(dataTable.getLlangid());
						desc.setFsLanguageType(ltype);
						desc.setNationality(dataTable.getLocalNationality());
					}
				
						saveDescList.add(desc);
				
				}
		}
		if (!saveMasterList.isEmpty()) {
			saveObject.put("master", saveMasterList);
			saveObject.put("desc", saveDescList);
			try {
				iCountryMasterservice.save(saveObject);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("succsses.show();");
			} catch (Exception e) {
				System.out.println("Exception e"+e);
				setErrorMessage("Data not saved "+ e.getMessage());
				RequestContext.getCurrentInstance().execute("sssave.show();");
				setErrorMessage(e.getMessage());
				
				LOGGER.info("setErrorMessage" + getErrorMessage());
				
			}
			finally{
				setBooFileUploaded(null);
			}
		}
		LOGGER.info("Exit into saveAll method");
	}

	
	
	public void saveUploadClick() {
		
		uploadrDataTableList.clear();
		setBooDtEnabled(false);
		System.out.println("Entering into pageNavigation");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/countrymasterUpload.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("Exit  into pageNavigation");
	}

	public void clickOExit() throws IOException {
		
		uploadrDataTableList.clear();
		setBooFileUploaded(null);
		setBooDtEnabled(false);
		if (sess.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	
}
