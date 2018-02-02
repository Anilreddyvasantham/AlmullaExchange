package com.amg.exchange.cashtransfer.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.model.CashDetails;
import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashDeposit")
@Scope("session")
public class CashDepositBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(CashDepositBean.class);

	private SessionStateManage sessionManage=new SessionStateManage();

	BigDecimal stock=null;
	//currency id for removing all related currencies
	BigDecimal currencyForList;
	private Map<BigDecimal, BigDecimal> denominationMap=new HashMap<BigDecimal, BigDecimal>();
	private List<StockCurrency> stockcurrencyList=new CopyOnWriteArrayList<StockCurrency>();
	private List<CashTransferForLToLBeanDataTable> cashTransferDataTableList=new ArrayList<CashTransferForLToLBeanDataTable>();
	private List<CashTransferForLToLBeanDataTable> cashTransferSampleList=new ArrayList<CashTransferForLToLBeanDataTable>(); // sample list for displaying

	@Autowired
	ICashTransferLToLService cashTransferLToLService;

	@Autowired
	IGeneralService generalService;

	BigDecimal currentStock;
	
	String branchName;
	String cashierName;
	String logoPath;

	public BigDecimal getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(BigDecimal currentStock) {
		this.currentStock = currentStock;
	}

	public List<CashTransferForLToLBeanDataTable> getCashTransferDataTableList() {
		return cashTransferDataTableList;
	}

	public void setCashTransferDataTableList(
			List<CashTransferForLToLBeanDataTable> cashTransferDataTableList) {
		this.cashTransferDataTableList = cashTransferDataTableList;
	}

	public void getAllCurrencyDenominationFromStock() {
		denominationMap.clear();
		getDenominationMap();
		stockcurrencyList.clear();
		cashTransferDataTableList.clear();
		List<Stock> denominationStock = cashTransferLToLService.getAllCurrencyDenominationFromStock(sessionManage.getCountryId(),
				sessionManage.getUserName(),
				new BigDecimal(sessionManage.getBranchId()),
				sessionManage.getCompanyId());
		if(denominationStock.size()>0)	{	

			for (Stock element : denominationStock) {
				//formula for calculating stock	
				stock = new BigDecimal(element.getOpenQuantity()).add(new BigDecimal(element.getPurchaseQuantity())).add(new BigDecimal(element.getReceivedQuantity())).subtract((new BigDecimal(element.getSaleQuantity()).add(new BigDecimal(element.getTransactionQuantity()))));
				StockCurrency stockCurrency=new StockCurrency();
				stockCurrency.setStockId(stock);
				stockCurrency.setDenominationId(element.getDenominationId().getDenominationId());
				stockCurrency.setCurrencyId(element.getCurrencyId().getCurrencyId());
				stockcurrencyList.add(stockCurrency);
			}
			for(StockCurrency stockCurrency:stockcurrencyList){
				System.out.println("DENOMINATION ID"+stockCurrency.getDenominationId());
				System.out.println("STOCK "+stockCurrency.getStockId());
				System.out.println("Currency Id"+stockCurrency.getCurrencyId());
			}


			for(int i=0;i<=stockcurrencyList.size()-1;){
				CashTransferForLToLBeanDataTable cashTransferDataTable=new CashTransferForLToLBeanDataTable();
				for(int j=i;j<=stockcurrencyList.size()-1;j++){
					StockCurrency stock=stockcurrencyList.get(i);
					StockCurrency stock1=stockcurrencyList.get(j);
					if(stock.getCurrencyId().equals(stock1.getCurrencyId())){
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.THOUSAND)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getThousands()==null ? BigDecimal.ZERO :cashTransferDataTable.getThousands()).add(stock1.getStockId());
								cashTransferDataTable.setThousands(totalStock);
							}

						}else{
							if( cashTransferDataTable.getThousands()==null )
							{
								cashTransferDataTable.setThousands(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.FIVE_HUNDRED)){

							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getFiveHundreds()==null ? BigDecimal.ZERO :cashTransferDataTable.getFiveHundreds()).add(stock1.getStockId());
								cashTransferDataTable.setFiveHundreds(totalStock);
							}
						} else{
							if(cashTransferDataTable.getFiveHundreds()==null)
							{
								cashTransferDataTable.setFiveHundreds(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.TWO_HUNDRED)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getTwoHundreds()==null ? BigDecimal.ZERO :cashTransferDataTable.getTwoHundreds()).add(stock1.getStockId());
								cashTransferDataTable.setTwoHundreds(totalStock);
							}
						} else{
							if(cashTransferDataTable.getTwoHundreds()==null){
								cashTransferDataTable.setTwoHundreds(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.HUNDRED)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getHundreds()==null ? BigDecimal.ZERO :cashTransferDataTable.getHundreds()).add(stock1.getStockId());
								cashTransferDataTable.setHundreds(totalStock);
							}
						} else{
							if(cashTransferDataTable.getHundreds()==null){
								cashTransferDataTable.setHundreds(BigDecimal.ZERO);
							}
						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.FIFTY)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getFifties()==null ? BigDecimal.ZERO :cashTransferDataTable.getFifties()).add(stock1.getStockId());
								cashTransferDataTable.setFifties(totalStock);
							}
						} else{
							if(cashTransferDataTable.getFifties()==null){
								cashTransferDataTable.setFifties(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.TWENTY)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getTwenties()==null ? BigDecimal.ZERO :cashTransferDataTable.getTwenties()).add(stock1.getStockId());
								cashTransferDataTable.setTwenties(totalStock);
							}
						}  else{
							if(cashTransferDataTable.getTwenties()==null){
								cashTransferDataTable.setTwenties(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.TEN)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getTens()==null ? BigDecimal.ZERO :cashTransferDataTable.getTens()).add(stock1.getStockId());
								cashTransferDataTable.setTens(totalStock);
							}
						}  else{
							if(cashTransferDataTable.getTens()==null)
							{
								cashTransferDataTable.setTens(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.FIVE)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getFives()==null ? BigDecimal.ZERO :cashTransferDataTable.getFives()).add(stock1.getStockId());
								cashTransferDataTable.setFives(totalStock);
							}
						}else{
							if(cashTransferDataTable.getFives()==null)
							{
								cashTransferDataTable.setFives(BigDecimal.ZERO);
							}

						} 
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.ONE)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getOnes()==null ? BigDecimal.ZERO :cashTransferDataTable.getOnes()).add(stock1.getStockId());
								cashTransferDataTable.setOnes(totalStock);
							}
						} else{
							if(cashTransferDataTable.getOnes()==null)
							{
								cashTransferDataTable.setOnes(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.POINT_FIVE)){	
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getPointfives()==null ? BigDecimal.ZERO :cashTransferDataTable.getPointfives()).add(stock1.getStockId());
								cashTransferDataTable.setPointfives(totalStock);
							}
						}else{
							if(cashTransferDataTable.getPointfives()==null){
								cashTransferDataTable.setPointfives(BigDecimal.ZERO);
							}

						} 
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.POINT_TWO_FIVE)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getPointTwoFives()==null ? BigDecimal.ZERO :cashTransferDataTable.getPointTwoFives()).add(stock1.getStockId());
								cashTransferDataTable.setPointTwoFives(totalStock);
							}
						}  else{
							if(cashTransferDataTable.getPointTwoFives()==null){
								cashTransferDataTable.setPointTwoFives(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.POINT_ONE)){
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getPointOnes()==null ? BigDecimal.ZERO :cashTransferDataTable.getPointOnes()).add(stock1.getStockId());
								cashTransferDataTable.setPointOnes(totalStock);
							}
						} else{
							if(cashTransferDataTable.getPointOnes()==null){
								cashTransferDataTable.setPointOnes(BigDecimal.ZERO);
							}

						}
						if(denominationMap.get(stock1.getDenominationId()).equals(Constants.POINT_ZERO_FIVE)){	
							if(stock1.getStockId()!=null){
								BigDecimal totalStock=(cashTransferDataTable.getPointZeroFives()==null ? BigDecimal.ZERO :cashTransferDataTable.getPointZeroFives()).add(stock1.getStockId());
								cashTransferDataTable.setPointZeroFives(totalStock);
							}
						} else{
							if(cashTransferDataTable.getPointZeroFives()==null){
								cashTransferDataTable.setPointZeroFives(BigDecimal.ZERO);	
							}

						}

						cashTransferDataTable.setCurrencyName(generalService.getCurrencyName(stock1.getCurrencyId()));
						cashTransferDataTable.setCurencyId(stock1.getCurrencyId());
						currencyForList=stock1.getCurrencyId();
					}

				}
				for(StockCurrency stock:stockcurrencyList){
					if(stock.getCurrencyId().equals(currencyForList)){
						stockcurrencyList.remove(stock);
					}
				}
				cashTransferSampleList.add(cashTransferDataTable);
				for(CashTransferForLToLBeanDataTable cashTransferDTObj:cashTransferSampleList){
					BigDecimal calTotalTransfer=
							(cashTransferDTObj.getThousands()==null ? BigDecimal.ZERO :cashTransferDTObj.getThousands()).multiply(Constants.THOUSAND)
							.add((cashTransferDTObj.getFiveHundreds()==null ? BigDecimal.ZERO :cashTransferDTObj.getFiveHundreds()).multiply(Constants.FIVE_HUNDRED))
							.add((cashTransferDTObj.getTwoHundreds()==null ? BigDecimal.ZERO :cashTransferDTObj.getTwoHundreds()).multiply(Constants.TWO_HUNDRED))
							.add((cashTransferDTObj.getHundreds()==null ? BigDecimal.ZERO :cashTransferDTObj.getHundreds()).multiply(Constants.HUNDRED)
									.add((cashTransferDTObj.getFifties()==null ? BigDecimal.ZERO :cashTransferDTObj.getFifties()).multiply(Constants.FIFTY))
									.add((cashTransferDTObj.getTwenties()==null ? BigDecimal.ZERO :cashTransferDTObj.getTwenties()).multiply(Constants.TWENTY))
									.add((cashTransferDTObj.getTens()==null ? BigDecimal.ZERO :cashTransferDTObj.getTens()).multiply(Constants.TEN))
									.add((cashTransferDTObj.getFives()==null ? BigDecimal.ZERO :cashTransferDTObj.getFives()).multiply(Constants.FIVE))
									.add((cashTransferDTObj.getOnes()==null ? BigDecimal.ZERO :cashTransferDTObj.getOnes()).multiply(Constants.ONE))
									.add((cashTransferDTObj.getPointfives()==null ? BigDecimal.ZERO :cashTransferDTObj.getPointfives()).multiply(Constants.POINT_FIVE))
									.add((cashTransferDTObj.getPointTwoFives()==null ? BigDecimal.ZERO :cashTransferDTObj.getPointTwoFives()).multiply(Constants.POINT_TWO_FIVE))
									.add((cashTransferDTObj.getPointOnes()==null ? BigDecimal.ZERO :cashTransferDTObj.getPointOnes()).multiply(Constants.POINT_ONE))
									.add((cashTransferDTObj.getPointZeroFives()==null ? BigDecimal.ZERO :cashTransferDTObj.getPointZeroFives()).multiply(Constants.POINT_ZERO_FIVE)));
					cashTransferDataTable.setTotalTransfer(calTotalTransfer);	
					calTotalTransfer=null;
				}

				cashTransferDataTableList.add(cashTransferDataTable);
				cashTransferSampleList.clear();
			}

		}	else{
			RequestContext.getCurrentInstance().execute("noStock.show();");
		}
	}

	public void getDenominationMap(){
		List<CurrencyWiseDenomination> denominationList=cashTransferLToLService.getAllCurrencyWiseDenomiantionList();
		for(CurrencyWiseDenomination denomination:denominationList){
			denominationMap.put(denomination.getDenominationId(), denomination.getDenominationAmount());
		}
	}



	//calculation with 1000 values 
	public void onCellEditThousands(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getThousandMap().size()>0){
			subtractTotalTransfer=cashTransObj.getThousandMap().get(Constants.THOUSAND).multiply(Constants.THOUSAND);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getThousandMap().remove(Constants.THOUSAND);
		}

		if((cashTransObj.getThousands().compareTo(cashTransObj.getInputThousands()==null ? BigDecimal.ZERO :cashTransObj.getInputThousands())>=0) 
				|| (cashTransObj.getInputThousands()==null ? BigDecimal.ZERO :cashTransObj.getInputThousands()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputThousands()!=null){
				//putting data table input value into map
				cashTransObj.getThousandMap().clear();
				cashTransObj.getThousandMap().put(Constants.THOUSAND, cashTransObj.getInputThousands());
				addTotalTransfer=cashTransObj.getInputThousands().multiply(Constants.THOUSAND);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getThousandMap().size()>0){
				subtractTotalTransfer=cashTransObj.getThousandMap().get(Constants.THOUSAND).multiply(Constants.THOUSAND);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getThousandMap().remove(Constants.THOUSAND);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getThousands());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputThousands(null);
			if(cashTransObj.getThousandMap().size()>0){
				subtractTotalTransfer=cashTransObj.getThousandMap().get(Constants.THOUSAND).multiply(Constants.THOUSAND);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getThousandMap().remove(Constants.THOUSAND);
			}

		}
	}

	//calculation with 500 values
	public void onCellEditFiveHundreds(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getFiveHundredMap().size()>0){
			subtractTotalTransfer=cashTransObj.getFiveHundredMap().get(Constants.FIVE_HUNDRED).multiply(Constants.FIVE_HUNDRED);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getFiveHundredMap().remove(Constants.FIVE_HUNDRED);
		}

		if((cashTransObj.getFiveHundreds().compareTo(cashTransObj.getInputFiveHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputFiveHundreds())>=0) 
				|| (cashTransObj.getInputFiveHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputFiveHundreds()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputFiveHundreds()!=null){
				//putting data table input value into map
				cashTransObj.getFiveHundredMap().clear();
				cashTransObj.getFiveHundredMap().put(Constants.FIVE_HUNDRED, cashTransObj.getInputFiveHundreds());
				addTotalTransfer=cashTransObj.getInputFiveHundreds().multiply(Constants.FIVE_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getFiveHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiveHundredMap().get(Constants.FIVE_HUNDRED).multiply(Constants.FIVE_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiveHundredMap().remove(Constants.FIVE_HUNDRED);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getFiveHundreds());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputFiveHundreds(null);
			if(cashTransObj.getFiveHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiveHundredMap().get(Constants.FIVE_HUNDRED).multiply(Constants.FIVE_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiveHundredMap().remove(Constants.FIVE_HUNDRED);
			}

		}
	}

	//calculation with 200 values
	public void onCellEditTwoHundreds(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getTwoHundredMap().size()>0){
			subtractTotalTransfer=cashTransObj.getTwoHundredMap().get(Constants.TWO_HUNDRED).multiply(Constants.TWO_HUNDRED);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getTwoHundredMap().remove(Constants.TWO_HUNDRED);
		}

		if((cashTransObj.getTwoHundreds().compareTo(cashTransObj.getInputTwoHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputTwoHundreds())>=0) 
				|| (cashTransObj.getInputTwoHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputTwoHundreds()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputTwoHundreds()!=null){
				//putting data table input value into map
				cashTransObj.getTwoHundredMap().clear();
				cashTransObj.getTwoHundredMap().put(Constants.TWO_HUNDRED, cashTransObj.getInputTwoHundreds());
				addTotalTransfer=cashTransObj.getInputTwoHundreds().multiply(Constants.TWO_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getTwoHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTwoHundredMap().get(Constants.TWO_HUNDRED).multiply(Constants.TWO_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTwoHundredMap().remove(Constants.TWO_HUNDRED);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getTwoHundreds());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputTwoHundreds(null);
			if(cashTransObj.getTwoHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTwoHundredMap().get(Constants.TWO_HUNDRED).multiply(Constants.TWO_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTwoHundredMap().remove(Constants.TWO_HUNDRED);
			}

		}
	}

	//calculation with 100 values
	public void onCellEditHundreds(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getHundredMap().size()>0){
			subtractTotalTransfer=cashTransObj.getHundredMap().get(Constants.HUNDRED).multiply(Constants.HUNDRED);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getHundredMap().remove(Constants.HUNDRED);
		}

		if((cashTransObj.getHundreds().compareTo(cashTransObj.getInputHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputHundreds())>=0) 
				|| (cashTransObj.getInputHundreds()==null ? BigDecimal.ZERO :cashTransObj.getInputHundreds()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputHundreds()!=null){
				//putting data table input value into map
				cashTransObj.getHundredMap().clear();
				cashTransObj.getHundredMap().put(Constants.HUNDRED, cashTransObj.getInputHundreds());
				addTotalTransfer=cashTransObj.getInputHundreds().multiply(Constants.HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getHundredMap().get(Constants.HUNDRED).multiply(Constants.HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getHundredMap().remove(Constants.HUNDRED);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getHundreds());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputHundreds(null);
			if(cashTransObj.getHundredMap().size()>0){
				subtractTotalTransfer=cashTransObj.getHundredMap().get(Constants.HUNDRED).multiply(Constants.HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getHundredMap().remove(Constants.HUNDRED);
			}

		}
	}

	//calculation with 50 values
	public void onCellEditFifties(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getFiftieMap().size()>0){
			subtractTotalTransfer=cashTransObj.getFiftieMap().get(Constants.FIFTY).multiply(Constants.FIFTY);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getFiftieMap().remove(Constants.FIFTY);
		}

		if((cashTransObj.getFifties().compareTo(cashTransObj.getInputFifties()==null ? BigDecimal.ZERO :cashTransObj.getInputFifties())>=0) 
				|| (cashTransObj.getInputFifties()==null ? BigDecimal.ZERO :cashTransObj.getInputFifties()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputFifties()!=null){
				//putting data table input value into map
				cashTransObj.getFiftieMap().clear();
				cashTransObj.getFiftieMap().put(Constants.FIFTY, cashTransObj.getInputFifties());
				addTotalTransfer=cashTransObj.getInputFifties().multiply(Constants.FIFTY);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getFiftieMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiftieMap().get(Constants.FIFTY).multiply(Constants.FIFTY);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiftieMap().remove(Constants.FIFTY);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getFifties());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputFifties(null);
			if(cashTransObj.getFiftieMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiftieMap().get(Constants.FIFTY).multiply(Constants.FIFTY);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiftieMap().remove(Constants.FIFTY);
			}

		}
	}

	//calculation with 20 values
	public void onCellEditTwenties(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getTwentieMap().size()>0){
			subtractTotalTransfer=cashTransObj.getTwentieMap().get(Constants.TWENTY).multiply(Constants.TWENTY);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getTwentieMap().remove(Constants.TWENTY);
		}

		if((cashTransObj.getTwenties().compareTo(cashTransObj.getInputTwenties()==null ? BigDecimal.ZERO :cashTransObj.getInputTwenties())>=0) 
				|| (cashTransObj.getInputTwenties()==null ? BigDecimal.ZERO :cashTransObj.getInputTwenties()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputTwenties()!=null){
				//putting data table input value into map
				cashTransObj.getTwentieMap().clear();
				cashTransObj.getTwentieMap().put(Constants.TWENTY, cashTransObj.getInputTwenties());
				addTotalTransfer=cashTransObj.getInputTwenties().multiply(Constants.TWENTY);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getTwentieMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTwentieMap().get(Constants.TWENTY).multiply(Constants.TWENTY);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTwentieMap().remove(Constants.TWENTY);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getTwenties());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputTwenties(null);
			if(cashTransObj.getTwentieMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTwentieMap().get(Constants.TWENTY).multiply(Constants.TWENTY);
				cashTransObj.setInputTotalTransfer( (cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTwentieMap().remove(Constants.TWENTY);
			}

		}
	}

	//calculation with 10 values
	public void onCellEditTens(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getTenMap().size()>0){
			subtractTotalTransfer=cashTransObj.getTenMap().get(Constants.TEN).multiply(Constants.TEN);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getTenMap().remove(Constants.TEN);
		}

		if((cashTransObj.getTens().compareTo(cashTransObj.getInputTens()==null ? BigDecimal.ZERO :cashTransObj.getInputTens())>=0) 
				|| (cashTransObj.getInputTens()==null ? BigDecimal.ZERO :cashTransObj.getInputTens()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputTens()!=null){
				//putting data table input value into map
				cashTransObj.getTenMap().clear();
				cashTransObj.getTenMap().put(Constants.TEN, cashTransObj.getInputTens());
				addTotalTransfer=cashTransObj.getInputTens().multiply(Constants.TEN);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getTenMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTenMap().get(Constants.TEN).multiply(Constants.TEN);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTenMap().remove(Constants.TEN);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getTens());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputTens(null);
			if(cashTransObj.getTenMap().size()>0){
				subtractTotalTransfer=cashTransObj.getTenMap().get(Constants.TEN).multiply(Constants.TEN);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getTenMap().remove(Constants.TEN);
			}

		}
	}

	//calculation with 5 values
	public void onCellEditFives(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getFiveMap().size()>0){
			subtractTotalTransfer=cashTransObj.getFiveMap().get(Constants.FIVE).multiply(Constants.FIVE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getFiveMap().remove(Constants.FIVE);
		}

		if((cashTransObj.getFives().compareTo(cashTransObj.getInputFives()==null ? BigDecimal.ZERO :cashTransObj.getInputFives())>=0) 
				|| (cashTransObj.getInputFives()==null ? BigDecimal.ZERO :cashTransObj.getInputFives()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputFives()!=null){
				//putting data table input value into map
				cashTransObj.getFiveMap().clear();
				cashTransObj.getFiveMap().put(Constants.FIVE, cashTransObj.getInputFives());
				addTotalTransfer=cashTransObj.getInputFives().multiply(Constants.FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiveMap().get(Constants.FIVE).multiply(Constants.FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiveMap().remove(Constants.FIVE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getFives());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputFives(null);
			if(cashTransObj.getFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getFiveMap().get(Constants.FIVE).multiply(Constants.FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getFiveMap().remove(Constants.FIVE);
			}

		}
	}

	//calculation with 1 values
	public void onCellEditOnes(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getOneMap().size()>0){
			subtractTotalTransfer=cashTransObj.getOneMap().get(Constants.ONE).multiply(Constants.ONE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getOneMap().remove(Constants.ONE);
		}

		if((cashTransObj.getOnes().compareTo(cashTransObj.getInputOnes()==null ? BigDecimal.ZERO :cashTransObj.getInputOnes())>=0) 
				|| (cashTransObj.getInputOnes()==null ? BigDecimal.ZERO :cashTransObj.getInputOnes()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputOnes()!=null){
				//putting data table input value into map
				cashTransObj.getOneMap().clear();
				cashTransObj.getOneMap().put(Constants.ONE, cashTransObj.getInputOnes());
				addTotalTransfer=cashTransObj.getInputOnes().multiply(Constants.ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else 	if(cashTransObj.getOneMap().size()>0){
				subtractTotalTransfer=cashTransObj.getOneMap().get(Constants.ONE).multiply(Constants.ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getOneMap().remove(Constants.ONE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getOnes());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputOnes(null);
			if(cashTransObj.getOneMap().size()>0){
				subtractTotalTransfer=cashTransObj.getOneMap().get(Constants.ONE).multiply(Constants.ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getOneMap().remove(Constants.ONE);
			}

		}
	}

	//calculation with 0.5 values
	public void onCellEditPointfives(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getPointfiveMap().size()>0){
			subtractTotalTransfer=cashTransObj.getPointfiveMap().get(Constants.POINT_FIVE).multiply(Constants.POINT_FIVE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
		}

		if((cashTransObj.getPointfives().compareTo(cashTransObj.getInputPointfives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointfives())>=0) 
				|| (cashTransObj.getInputPointfives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointfives()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputPointfives()!=null){
				//putting data table input value into map
				cashTransObj.getPointfiveMap().clear();
				cashTransObj.getPointfiveMap().put(Constants.POINT_FIVE, cashTransObj.getInputPointfives());
				addTotalTransfer=cashTransObj.getInputPointfives().multiply(Constants.POINT_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else 	if(cashTransObj.getPointfiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointfiveMap().get(Constants.POINT_FIVE).multiply(Constants.POINT_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getPointfives());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputPointfives(null);
			if(cashTransObj.getPointfiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointfiveMap().get(Constants.POINT_FIVE).multiply(Constants.POINT_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
			}

		}
	}

	//calculation with 0.25 values
	public void onCellEditPointTwoFives(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getPointTwoFiveMap().size()>0){
			subtractTotalTransfer=cashTransObj.getPointTwoFiveMap().get(Constants.POINT_TWO_FIVE).multiply(Constants.POINT_TWO_FIVE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getPointTwoFiveMap().remove(Constants.POINT_TWO_FIVE);
		}

		if((cashTransObj.getPointTwoFives().compareTo(cashTransObj.getInputPointTwoFives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointTwoFives())>=0) 
				|| (cashTransObj.getInputPointTwoFives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointTwoFives()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputPointTwoFives()!=null){
				//putting data table input value into map
				cashTransObj.getPointTwoFiveMap().clear();
				cashTransObj.getPointTwoFiveMap().put(Constants.POINT_TWO_FIVE, cashTransObj.getInputPointTwoFives());
				addTotalTransfer=cashTransObj.getInputPointTwoFives().multiply(Constants.POINT_TWO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getPointTwoFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointTwoFiveMap().get(Constants.POINT_TWO_FIVE).multiply(Constants.POINT_TWO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointTwoFiveMap().remove(Constants.POINT_TWO_FIVE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getPointTwoFives());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputPointTwoFives(null);
			if(cashTransObj.getPointTwoFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointTwoFiveMap().get(Constants.POINT_TWO_FIVE).multiply(Constants.POINT_TWO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointTwoFiveMap().remove(Constants.POINT_TWO_FIVE);
			}

		}
	}

	//calculation with 0.1 values
	public void onCellEditPointOnes(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getPointOneMap().size()>0){
			subtractTotalTransfer=cashTransObj.getPointOneMap().get(Constants.POINT_ONE).multiply(Constants.POINT_ONE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
		}

		if((cashTransObj.getPointOnes().compareTo(cashTransObj.getInputPointOnes()==null ? BigDecimal.ZERO :cashTransObj.getInputPointOnes())>=0) 
				|| (cashTransObj.getInputPointOnes()==null ? BigDecimal.ZERO :cashTransObj.getInputPointOnes()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputPointOnes()!=null){
				//putting data table input value into map
				cashTransObj.getPointOneMap().clear();
				cashTransObj.getPointOneMap().put(Constants.POINT_ONE, cashTransObj.getInputPointOnes());
				addTotalTransfer=cashTransObj.getInputPointOnes().multiply(Constants.POINT_ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getPointOneMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointOneMap().get(Constants.POINT_ONE).multiply(Constants.POINT_ONE);
				cashTransObj.setInputTotalTransfer( (cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getPointOnes());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputPointOnes(null);
			if(cashTransObj.getPointOneMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointOneMap().get(Constants.POINT_ONE).multiply(Constants.POINT_ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
			}

		}
	}

	//calculation with 0.05 values
	public void onCellEditPointZeroFives(CashTransferForLToLBeanDataTable cashTransObj){
		BigDecimal addTotalTransfer=null;
		BigDecimal subtractTotalTransfer=null;
		if(cashTransObj.getPointZeroFiveMap().size()>0){
			subtractTotalTransfer=cashTransObj.getPointZeroFiveMap().get(Constants.POINT_ZERO_FIVE).multiply(Constants.POINT_ZERO_FIVE);
			cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
			cashTransObj.getPointZeroFiveMap().remove(Constants.POINT_ZERO_FIVE);
		}

		if((cashTransObj.getPointZeroFives().compareTo(cashTransObj.getInputPointZeroFives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointZeroFives())>=0) 
				|| (cashTransObj.getInputPointZeroFives()==null ? BigDecimal.ZERO :cashTransObj.getInputPointZeroFives()).compareTo(BigDecimal.ZERO)==0){
			if(cashTransObj.getInputPointZeroFives()!=null){
				//putting data table input value into map
				cashTransObj.getPointZeroFiveMap().clear();
				cashTransObj.getPointZeroFiveMap().put(Constants.POINT_ZERO_FIVE, cashTransObj.getInputPointZeroFives());
				addTotalTransfer=cashTransObj.getInputPointZeroFives().multiply(Constants.POINT_ZERO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).add(addTotalTransfer));
			}else if(cashTransObj.getPointZeroFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointZeroFiveMap().get(Constants.POINT_ZERO_FIVE).multiply(Constants.POINT_ZERO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointZeroFiveMap().remove(Constants.POINT_ZERO_FIVE);
			}
		}else{
			setCurrentStock(null);
			setCurrentStock(cashTransObj.getPointZeroFives());
			RequestContext.getCurrentInstance().execute("stockcheck.show();");
			cashTransObj.setInputPointZeroFives(null);
			if(cashTransObj.getPointZeroFiveMap().size()>0){
				subtractTotalTransfer=cashTransObj.getPointZeroFiveMap().get(Constants.POINT_ZERO_FIVE).multiply(Constants.POINT_ZERO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj.getInputTotalTransfer()==null ? BigDecimal.ZERO :cashTransObj.getInputTotalTransfer()).subtract(subtractTotalTransfer));
				cashTransObj.getPointZeroFiveMap().remove(Constants.POINT_ZERO_FIVE);
			}

		}
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if(newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void cashDepositNavigation(){
		
		clearAll();
		currencyStockHeaderBeanList.clear();
		currencyStockDetailBeanList.clear();
		currencyStockReportBeanList.clear();
		
		cashTransferDataTableList.clear();
		currencyStockDataTableList.clear();
		preprocessingMethods();
		//	 addRecordsToDataTable();
		setRenderDataTable(true);		
		//cashTransferDataTableList.clear();
		//cashTallyReportList.clear();
		
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "CashDeposit.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../cashtransfer/CashDeposit.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<CashTallyReportBean> cashTallyReportList=new ArrayList<CashTallyReportBean>();	
	// List<CashTallySubReportBean> cashTallySubReportList=new ArrayList<CashTallySubReportBean>();	

	public List<CashTallyReportBean> getCashTallyReportList() {
		return cashTallyReportList;
	}

	public void setCashTallyReportList(List<CashTallyReportBean> cashTallyReportList) {
		this.cashTallyReportList = cashTallyReportList;
	}

	public void pepreportGeneration(){
		CashTallyReportBean cashTallyReportBean=new CashTallyReportBean();
		List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		cashTallyReportBean.setBranchName(branchList.get(0).getBranchName());
		cashTallyReportBean.setCashierName(sessionManage.getUserName());
		cashTallyReportBean.setCurrencyName("INR");
		cashTallyReportBean.setLineNo(Constants.ONE);
		cashTallyReportBean.setDenominations("99 KWD");
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		cashTallyReportBean.setLogoPath(logoPath);
		//	String subReport = realPath +Constants.SUB_REPORT_PATH;
		//	cashTallyReportBean.setSubReport(subReport);

		/*CashTallySubReportBean cashTallySubReportBean=new CashTallySubReportBean();
	      cashTallySubReportBean.setDenominations("99 INR");
	      cashTallySubReportBean.setPhysicalNotes(new BigDecimal(1));
	      cashTallySubReportBean.setPhysicalAmount(new BigDecimal(10));
	      cashTallySubReportBean.setSystemNotes(new BigDecimal(20));
	      cashTallySubReportBean.setSystemAmount(new BigDecimal(30));
	      cashTallySubReportBean.setDifference(new BigDecimal(10));
	      cashTallySubReportBean.setTotalSystemAmount(new BigDecimal(10));
	      cashTallySubReportBean.setTotalPhysicalAmount(new BigDecimal(20));
	      cashTallySubReportList.add(cashTallySubReportBean);
	      cashTallyReportBean.setCashTallyReportList(cashTallySubReportList);*/
		//cashTallyReportBean.setLinoList(Arrays.asList("London","GEMBABOY"));
		//cashTallyReportList.add(cashTallyReportBean);
	}

	public Map<String,Object> getMap(){
		Map<String,Object> newMap=new HashMap<String,Object>();

		return newMap;
	}

	
	private BigDecimal fromLocation;
	private String fromLocationFullName;
	private String toLocation;
	private String fromCashier;
	private String toCashier;
	private String toCashierName;
	private String transferOption;
	
	private List<CountryBranch> lstCountryBranchForLocation = new ArrayList<CountryBranch>();
	private List<CountryBranch> lstCountryBranchToLocation = new ArrayList<CountryBranch>();
	private List<CurrencyStockDataTable> currencyStockDataTableList = new CopyOnWriteArrayList<CurrencyStockDataTable>();
	
	private List<CurrencyStockHeaderBean> currencyStockHeaderBeanList = new CopyOnWriteArrayList<CurrencyStockHeaderBean>();
	private List<CurrencyStockDetailBean> currencyStockDetailBeanList = new CopyOnWriteArrayList<CurrencyStockDetailBean>();
	private List<CurrencyStockReportBean> currencyStockReportBeanList = new CopyOnWriteArrayList<CurrencyStockReportBean>();
	
	private List<BigDecimal> currencyList = new CopyOnWriteArrayList<BigDecimal>();
	private boolean booNilCurrency = false;
	
	private String warningMessage;
	private Boolean renderDataTable=false;
	
	
	public void locationtoLocationByCountry(){
		lstCountryBranchForLocation.clear();
		List<CountryBranch> lstCountryBranch = generalService.getBranchDetails(sessionManage.getCountryId());

		if(lstCountryBranch.size()!=0){
			lstCountryBranchForLocation.addAll(lstCountryBranch);
		}

	}

	public void preprocessingMethods(){
		//locationtoLocationByCountry();
		setFromLocation(new BigDecimal(sessionManage.getBranchId()));
		List<CountryBranch> lstCountryBranch = generalService.getBranchDetailsFromBeneStatus(sessionManage.getCountryId(), getFromLocation());
		for (CountryBranch countryBranch : lstCountryBranch) {
			setFromLocationFullName(countryBranch.getBranchName());
		}
		getCurrencyDenominationFromStock();
		fromToLocationCountryBranch();
		
	}

	public void fromToLocationCountryBranch(){
		try{
			cashTransferDataTableList.clear();
			lstCountryBranchToLocation.clear();
			setToLocation(null);
			setTransferOption(null);
			// To Location according to From Location
			List<CountryBranch> lstToCountryBranch = generalService.getBranchDetailsForToLocation(sessionManage.getCountryId(),getFromLocation());

			if(lstToCountryBranch.size()!=0){
				lstCountryBranchToLocation.addAll(lstToCountryBranch);
			}

			//Cashier is setting Default Safe -  For FROM AND TO CASHIER
			setFromCashier(Constants.SAFE);
			setToCashier(Constants.SAFE);
			//getCurrencyDenominationFromStock();
			
		} catch(Exception e){
			if(e.getMessage()!=null){
				setWarningMessage("Exception :"+e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}else{
				setWarningMessage("Exception :"+e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}

	}

	public void clearAll(){
		lstCountryBranchToLocation.clear();
		setFromLocation(null);
		setToLocation(null);
		setFromCashier(null);
		setToCashier(null);
		setTransferOption(null);
	}
	
	
	public void getCurrencyDenominationFromStock() {

		try {

			setRenderDataTable(true);

			BigDecimal zero = BigDecimal.ZERO;

			currencyStockDataTableList.clear();
			CurrencyStockDataTable currencyStockDataTable = null;

			currencyList = cashTransferLToLService
					.getStockCurrency(getFromLocation());

			for (BigDecimal currency : currencyList) {
				List<UserStockView> denominationStock = cashTransferLToLService
						.getCurrencyDenominationFromStock(sessionManage
								.getCountryId(), sessionManage.getUserName(),
								getFromLocation(),
								sessionManage.getCompanyId(), new BigDecimal(
										currency.intValue()));
				{
					currencyStockDataTable = new CurrencyStockDataTable();

					for (UserStockView element : denominationStock) {
						if (element.getDenominationAmount().compareTo(
								Constants.ONE_LAKH) == 0) {
							currencyStockDataTable.setOneLakhDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setOneLakh(BigDecimal.ZERO);
								currencyStockDataTable
										.setOneLakhs(BigDecimal.ZERO);
							} else {
								
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								
								currencyStockDataTable.setOneLakh(element.getOpenQuantity());
								currencyStockDataTable.setOneLakhs(BigDecimal.ZERO);
								currencyStockDataTable.setOneLakhDenomId(element.getDenominationId());
								currencyStockDataTable.setOneLakhAdj(currencyStockDataTable.getOneLakhs().multiply(Constants.ONE_LAKH));
								currencyStockDataTable.setOneLakhPNotes(element.getOpenQuantity());
	
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY_THOUSAND) == 0) {
							currencyStockDataTable.setFiftyThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiftyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftyThousands(BigDecimal.ZERO);

							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setFiftyThousand(element.getOpenQuantity());
								currencyStockDataTable
										.setFiftyThousands(BigDecimal.ZERO);
								currencyStockDataTable.setFiftyThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setFiftyThousandAdj(currencyStockDataTable.getFiftyThousands().multiply(Constants.FIFTY_THOUSAND));
								currencyStockDataTable.setFiftyThousandPNotes(element.getOpenQuantity());
	
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY_THOUSAND) == 0) {
							currencyStockDataTable.setTwentyThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setTwentyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentyThousands(BigDecimal.ZERO);

							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable
										.setTwentyThousand(element.getOpenQuantity());
								currencyStockDataTable
										.setTwentyThousands(BigDecimal.ZERO);
								currencyStockDataTable.setTwentyThousandAdj(currencyStockDataTable.getTwentyThousands().multiply(Constants.TWENTY_THOUSAND));
								currencyStockDataTable.setTwentyThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setTwentyThousandPNotes(element.getOpenQuantity());
		
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TEN_THOUSAND) == 0) {
							currencyStockDataTable.setTenThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setTenThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTenThousands(BigDecimal.ZERO);

							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTenThousand(element.getOpenQuantity());
								currencyStockDataTable.setTenThousands(BigDecimal.ZERO);
								currencyStockDataTable.setTenThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setTenThousandAdj(currencyStockDataTable.getTenThousands().multiply(Constants.TEN_THOUSAND));
								currencyStockDataTable.setTenThousandPNotes(element.getOpenQuantity());
			
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_THOUSAND) == 0) {
							currencyStockDataTable.setFiveThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiveThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveThousands(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setFiveThousand(element.getOpenQuantity());
								currencyStockDataTable.setFiveThousands(BigDecimal.ZERO);
								currencyStockDataTable.setFiveThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setFiveThousandAdj(currencyStockDataTable.getFiveThousands().multiply(Constants.FIVE_THOUSAND));
								currencyStockDataTable.setFiveThousandPNotes(element.getOpenQuantity());
			
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_THOUSAND) == 0) {
							currencyStockDataTable.setTwoThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setTwoThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoThousands(BigDecimal.ZERO);

							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTwoThousand(element.getOpenQuantity());
								currencyStockDataTable.setTwoThousands(BigDecimal.ZERO);
								currencyStockDataTable.setTwoThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setTwoThousandAdj(currencyStockDataTable.getTwoThousands().multiply(Constants.TWO_THOUSAND));
								currencyStockDataTable.setTwoThousandPNotes(element.getOpenQuantity());
			
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.THOUSAND) == 0) {
							currencyStockDataTable.setThousandDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setThousands(BigDecimal.ZERO);

							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setThousand(element.getOpenQuantity());
								currencyStockDataTable.setThousands(BigDecimal.ZERO);
								currencyStockDataTable.setThousandDenomId(element.getDenominationId());
								currencyStockDataTable.setThousandAdj(currencyStockDataTable.getThousands().multiply(Constants.THOUSAND));
								currencyStockDataTable.setThousandPNotes(element.getOpenQuantity());
		
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_HUNDRED) == 0) {
							currencyStockDataTable.setFiveHundredDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable.setBooNilCurrency(true);
								currencyStockDataTable
										.setFiveHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveHundreds(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setFiveHundred(element.getOpenQuantity());
								currencyStockDataTable.setFiveHundreds(BigDecimal.ZERO);
								currencyStockDataTable.setFiveHundredDenomId(element.getDenominationId());
								currencyStockDataTable.setFiveHundredAdj(currencyStockDataTable.getThousands().multiply(Constants.FIVE_HUNDRED));
								currencyStockDataTable.setFiveHundredPNotes(element.getOpenQuantity());
			
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_HUNDRED) == 0) {
							currencyStockDataTable.setTwoHundredDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable.setBooNilCurrency(true);
								currencyStockDataTable
										.setTwoHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoHundreds(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTwoHundred(element.getOpenQuantity());
								currencyStockDataTable.setTwoHundreds(BigDecimal.ZERO);
								currencyStockDataTable.setTwoHundredDenomId(element.getDenominationId());
								currencyStockDataTable.setTwoHundredAdj(currencyStockDataTable.getThousands().multiply(Constants.TWO_HUNDRED));
								currencyStockDataTable.setTwoHundredPNotes(element.getOpenQuantity());
								
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.HUNDRED) == 0) {
							currencyStockDataTable.setHundredDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setHundred(zero);
								currencyStockDataTable.setHundreds(zero);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setHundred(element.getOpenQuantity());
								currencyStockDataTable.setHundreds(BigDecimal.ZERO);
								currencyStockDataTable.setHundredDenomId(element.getDenominationId());
								currencyStockDataTable.setHundredAdj(currencyStockDataTable.getHundreds().multiply(Constants.HUNDRED));
								currencyStockDataTable.setHundredPNotes(element.getOpenQuantity());
		
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY) == 0) {
							currencyStockDataTable.setFiftyDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setFifty(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftys(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setFifty(element.getOpenQuantity());
								currencyStockDataTable.setFiftys(BigDecimal.ZERO);
								currencyStockDataTable.setFiftyDenomId(element.getDenominationId());
								currencyStockDataTable.setFiftyAdj(currencyStockDataTable.getFiftys().multiply(Constants.FIFTY));
								currencyStockDataTable.setFiftyPNotes(element.getOpenQuantity());
			
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY) == 0) {
							currencyStockDataTable.setTwentyDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwenty(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentys(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTwenty(element.getOpenQuantity());
								currencyStockDataTable.setTwentys(BigDecimal.ZERO);
								currencyStockDataTable.setTwentyDenomId(element.getDenominationId());
								currencyStockDataTable.setTwentyAdj(currencyStockDataTable.getTwentys().multiply(Constants.TWENTY));
								currencyStockDataTable.setTwentyPNotes(element.getOpenQuantity());
			
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TEN) == 0) {
							currencyStockDataTable.setTenDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTen(BigDecimal.ZERO);
								currencyStockDataTable.setTens(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTen(element.getOpenQuantity());
								currencyStockDataTable.setTens(BigDecimal.ZERO);
								currencyStockDataTable.setTenDenomId(element.getDenominationId());
								currencyStockDataTable.setTenAdj(currencyStockDataTable.getTens().multiply(Constants.TEN));
								currencyStockDataTable.setTenPNotes(element.getOpenQuantity());
					
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE) == 0) {
							currencyStockDataTable.setFiveDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setFives(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setFive(element.getOpenQuantity());
								currencyStockDataTable.setFives(BigDecimal.ZERO);
								currencyStockDataTable.setFiveDenomId(element.getDenominationId());
								currencyStockDataTable.setFiveAdj(currencyStockDataTable.getFives().multiply(Constants.FIVE));
								currencyStockDataTable.setFivePNotes(element.getOpenQuantity());
				
							}
						}

						if (element.getDenominationAmount().compareTo(
								new BigDecimal(Constants.TWO)) == 0) {
							currencyStockDataTable.setTwoDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTwo(BigDecimal.ZERO);
								currencyStockDataTable.setTwos(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setTwo(element.getOpenQuantity());
								currencyStockDataTable.setTwos(BigDecimal.ZERO);
								currencyStockDataTable.setTwoDenomId(element.getDenominationId());
								currencyStockDataTable.setTwoAdj(currencyStockDataTable.getTwos().multiply(new BigDecimal(Constants.TWO)));
								currencyStockDataTable.setTwoPNotes(element.getOpenQuantity());
				
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.ONE) == 0) {
							currencyStockDataTable.setOneDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setOne(BigDecimal.ZERO);
								currencyStockDataTable.setOnes(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setOne(element.getOpenQuantity());
								currencyStockDataTable.setOnes(BigDecimal.ZERO);
								currencyStockDataTable.setOneDenomId(element.getDenominationId());
								currencyStockDataTable.setOneAdj(currencyStockDataTable.getOnes().multiply(Constants.ONE));
								currencyStockDataTable.setOnePNotes(element.getOpenQuantity());
					
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_FIVE) == 0) {
							currencyStockDataTable.setPointFiveDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointFives(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointFive(element.getOpenQuantity());
								currencyStockDataTable.setPointFives(BigDecimal.ZERO);
								currencyStockDataTable.setPointFiveDenomId(element.getDenominationId());
								currencyStockDataTable.setPointFiveAdj(currencyStockDataTable.getPointFives().multiply(Constants.POINT_FIVE));
								currencyStockDataTable.setPointFivePNotes(element.getOpenQuantity());
						
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_TWO_FIVE) == 0) {
							currencyStockDataTable.setPointTwoFiveDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointTwoFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointTwoFives(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointTwoFive(element.getOpenQuantity());
								currencyStockDataTable.setPointTwoFives(BigDecimal.ZERO);
								currencyStockDataTable.setPointTwoFiveDenomId(element.getDenominationId());
								currencyStockDataTable.setPointTwoFiveAdj(currencyStockDataTable.getPointTwoFives().multiply(Constants.POINT_TWO_FIVE));
								currencyStockDataTable.setPointTwoFivePNotes(element.getOpenQuantity());
						
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ONE) == 0) {
							currencyStockDataTable.setPointOneDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointOnes(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointOne(element.getOpenQuantity());
								currencyStockDataTable.setPointOnes(BigDecimal.ZERO);
								currencyStockDataTable.setPointOneDenomId(element.getDenominationId());
								currencyStockDataTable.setPointOneAdj(currencyStockDataTable.getPointOnes().multiply(Constants.POINT_ONE));
								currencyStockDataTable.setPointOnePNotes(element.getOpenQuantity());
				
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_FIVE) == 0) {
							currencyStockDataTable.setPointZeroFiveDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroFives(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointZeroFive(element.getOpenQuantity());
								currencyStockDataTable
										.setPointZeroFives(BigDecimal.ZERO);
								currencyStockDataTable.setPointZeroFiveDenomId(element.getDenominationId());
								currencyStockDataTable.setPointZeroFiveAdj(currencyStockDataTable.getPointZeroFives().multiply(Constants.POINT_ZERO_FIVE));
								currencyStockDataTable.setPointZeroFivePNotes(element.getOpenQuantity());
						
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_TWO) == 0) {
							currencyStockDataTable.setPointZeroTwoDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroTwo(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroTwos(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointZeroTwo(element.getOpenQuantity());
								currencyStockDataTable.setPointZeroTwos(BigDecimal.ZERO);
								currencyStockDataTable.setPointZeroTwoDenomId(element.getDenominationId());
								currencyStockDataTable.setPointZeroTwoAdj(currencyStockDataTable.getPointZeroTwos().multiply(Constants.POINT_ZERO_TWO));
								currencyStockDataTable.setPointZeroTwoPNotes(element.getOpenQuantity());
					
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ONE) == 0) {
							currencyStockDataTable.setPointZeroOneDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroOnes(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable.setPointZeroOne(element.getOpenQuantity());
								currencyStockDataTable.setPointZeroOnes(BigDecimal.ZERO);
								currencyStockDataTable.setPointZeroOneDenomId(element.getDenominationId());
								currencyStockDataTable.setPointZeroOneAdj(currencyStockDataTable.getPointZeroOnes().multiply(Constants.POINT_ZERO_ONE));
								currencyStockDataTable.setPointZeroOnePNotes(element.getOpenQuantity());
							
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_FIVE) == 0) {
							currencyStockDataTable.setPointZeroZeroFiveDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroFives(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable
										.setPointZeroZeroFive(element.getOpenQuantity());
								currencyStockDataTable
										.setPointZeroZeroFives(BigDecimal.ZERO);
								currencyStockDataTable.setPointZeroZeroFiveDenomId(element.getDenominationId());
								currencyStockDataTable.setPointZeroZeroFiveAdj(currencyStockDataTable.getPointZeroZeroFives().multiply(Constants.POINT_ZERO_ZERO_FIVE));
								currencyStockDataTable.setPointZeroZeroFivePNotes(element.getOpenQuantity());
							
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_ONE) == 0) {
							currencyStockDataTable.setPointZeroZeroOneDenomAmount(element.getDenominationAmount());
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroOnes(BigDecimal.ZERO);
							} else {
								stock = element.getOpenQuantity().add(element.getPurchaseQuantity()).add(element.getReceivedQuantity()).subtract(element.getSaleQuantity()).add(element.getTransactionQuantity());
								
								currencyStockDataTable
										.setPointZeroZeroOne(element.getOpenQuantity());
								currencyStockDataTable
										.setPointZeroZeroOnes(BigDecimal.ZERO);
								currencyStockDataTable.setPointZeroZeroOneDenomId(element.getDenominationId());
								currencyStockDataTable.setPointZeroZeroOneAdj(currencyStockDataTable.getPointZeroZeroOnes().multiply(Constants.POINT_ZERO_ZERO_ONE));
								currencyStockDataTable.setPointZeroZeroOnePNotes(element.getOpenQuantity());
						
							}
						}

						currencyStockDataTable.setCurrencyName(element
								.getCurrencyName());
						currencyStockDataTable.setCurrencyId(element
								.getCurrencyId());

						// Calculate currency
						getCalculateHeaderCurrency(currencyStockDataTable);
						getReadOnly(currencyStockDataTable);

					}
				}
				currencyStockDataTableList.add(currencyStockDataTable);
			}			
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../cashtransfer/CashDeposit.xhtml");
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// Calculate header currency total
	public void getCalculateHeaderCurrency(
			CurrencyStockDataTable currencyStockDataTable) {

		try {

			BigDecimal headerTotal = BigDecimal.ZERO;
			BigDecimal modifySubTotal = BigDecimal.ZERO;

			
			if (currencyStockDataTable.getOneLakh() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getOneLakh().multiply(Constants.ONE_LAKH));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOneLakhs().multiply(Constants.ONE_LAKH));
			}
			if (currencyStockDataTable.getFiftyThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiftyThousand().multiply(Constants.FIFTY_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftyThousands().multiply(Constants.FIFTY_THOUSAND));
			}
			

			if (currencyStockDataTable.getTwentyThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwentyThousand().multiply(Constants.TWENTY_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentyThousands().multiply(Constants.TWENTY_THOUSAND));
			}
			
			if (currencyStockDataTable.getTenThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTenThousand().multiply(Constants.TEN_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTenThousands().multiply(Constants.TEN_THOUSAND));
			}
			if (currencyStockDataTable.getFiveThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiveThousand().multiply(Constants.FIVE_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveThousands().multiply(Constants.FIVE_THOUSAND));
			}
			if (currencyStockDataTable.getTwoThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwoThousand().multiply(Constants.TWO_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoThousands().multiply(Constants.TWO_THOUSAND));
			}			
			if (currencyStockDataTable.getThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getThousand().multiply(Constants.THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getThousands().multiply(Constants.THOUSAND));
			}	
			if (currencyStockDataTable.getFiveHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiveHundred().multiply(Constants.FIVE_HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveHundreds().multiply(Constants.FIVE_HUNDRED));
			}
			if (currencyStockDataTable.getTwoHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwoHundred().multiply(Constants.TWO_HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoHundreds().multiply(Constants.TWO_HUNDRED));
				currencyStockDataTable.setTwoHundredAdj(modifySubTotal);
			}
			if (currencyStockDataTable.getHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getHundred().multiply(Constants.HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getHundreds().multiply(Constants.HUNDRED));
			}
			if (currencyStockDataTable.getFifty() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getFifty()
						.multiply(Constants.FIFTY));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftys().multiply(Constants.FIFTY));
			}
			if (currencyStockDataTable.getTwenty() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwenty().multiply(Constants.TWENTY));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentys().multiply(Constants.TWENTY));
			}
			if (currencyStockDataTable.getTen() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getTen()
						.multiply(Constants.TEN));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTens().multiply(Constants.TEN));
			}

			if (currencyStockDataTable.getFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getFive()
						.multiply(Constants.FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFives().multiply(Constants.FIVE));
			}

			if (currencyStockDataTable.getTwo() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getTwo()
						.multiply(new BigDecimal(Constants.TWO)));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwos().multiply(new BigDecimal(Constants.TWO)));
			}
			if (currencyStockDataTable.getOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getOne()
						.multiply(Constants.ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOnes().multiply(Constants.ONE));
			}
			if (currencyStockDataTable.getPointFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointFive().multiply(Constants.POINT_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointFives().multiply(Constants.POINT_FIVE));
			}
			if (currencyStockDataTable.getPointTwoFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointTwoFive().multiply(Constants.POINT_TWO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointTwoFives().multiply(Constants.POINT_TWO_FIVE));
			}
			if (currencyStockDataTable.getPointOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointOne().multiply(Constants.POINT_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointOnes().multiply(Constants.POINT_ONE));
			}
			if (currencyStockDataTable.getPointZeroFive() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroFive()
								.multiply(Constants.POINT_ZERO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroFives()
						.multiply(Constants.POINT_ZERO_FIVE));
			}
			if (currencyStockDataTable.getPointZeroTwo() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroTwo()
								.multiply(Constants.POINT_ZERO_TWO));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroTwos()
						.multiply(Constants.POINT_ZERO_TWO));
			}
			if (currencyStockDataTable.getPointZeroOne() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroOne()
								.multiply(Constants.POINT_ZERO_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroOnes()
						.multiply(Constants.POINT_ZERO_ONE));
			}
			if (currencyStockDataTable.getPointZeroZeroFive() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroZeroFive()
								.multiply(Constants.POINT_ZERO_ZERO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroFives()
						.multiply(Constants.POINT_ZERO_ZERO_FIVE));
			}
			if (currencyStockDataTable.getPointZeroZeroOne() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroZeroOne()
								.multiply(Constants.POINT_ZERO_ZERO_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroOnes()
						.multiply(Constants.POINT_ZERO_ZERO_ONE));
			}
			currencyStockDataTable.setRowTotal(headerTotal);
			currencyStockDataTable.setModRowTotal(modifySubTotal);
			currencyStockDataTable.setModRowTotalDeposit(headerTotal);
			getReadOnly(currencyStockDataTable);

		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// Calculate modify currency total
	public void getModifiedCurrencyValues(
			CurrencyStockDataTable currencyStockDataTable) {

		try {
			BigDecimal modifySubTotal = BigDecimal.ZERO;
			
			if (currencyStockDataTable.getOneLakhs() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOneLakhs().multiply(Constants.ONE_LAKH));
			}
			if (currencyStockDataTable.getFiftyThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftyThousands().multiply(Constants.FIFTY_THOUSAND));
			}
			if (currencyStockDataTable.getTwentyThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentyThousands().multiply(Constants.TWENTY_THOUSAND));
			}
			if (currencyStockDataTable.getTenThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTenThousands().multiply(Constants.TEN_THOUSAND));
			}
			if (currencyStockDataTable.getFiveThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveThousands().multiply(Constants.FIVE_THOUSAND));
			}
			if (currencyStockDataTable.getTwoThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoThousands().multiply(Constants.TWO_THOUSAND));
			}
			
			if (currencyStockDataTable.getThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getThousands().multiply(Constants.THOUSAND));
			}

			if (currencyStockDataTable.getFiveHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveHundreds().multiply(Constants.FIVE_HUNDRED));
			}
			if (currencyStockDataTable.getTwoHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoHundreds().multiply(Constants.TWO_HUNDRED));
			}
			if (currencyStockDataTable.getHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getHundreds().multiply(Constants.HUNDRED));
			}
			if (currencyStockDataTable.getFiftys() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftys().multiply(Constants.FIFTY));
			}
			if (currencyStockDataTable.getTwentys() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentys().multiply(Constants.TWENTY));
			}
			if (currencyStockDataTable.getTens() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTens().multiply(Constants.TEN));
			}
			if (currencyStockDataTable.getFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFives().multiply(Constants.FIVE));
			}

			if (currencyStockDataTable.getTwos() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwos().multiply(new BigDecimal(Constants.TWO)));
			}
			if (currencyStockDataTable.getOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOnes().multiply(Constants.ONE));
			}
			if (currencyStockDataTable.getPointFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointFives().multiply(Constants.POINT_FIVE));
			}
			if (currencyStockDataTable.getPointTwoFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointTwoFives().multiply(Constants.POINT_TWO_FIVE));
			}
			if (currencyStockDataTable.getPointOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointOnes().multiply(Constants.POINT_ONE));

			}
			if (currencyStockDataTable.getPointZeroFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroFives()
						.multiply(Constants.POINT_ZERO_FIVE));
			}
			
			if (currencyStockDataTable.getPointZeroTwo() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroTwos()
						.multiply(Constants.POINT_ZERO_TWO));
			}
			if (currencyStockDataTable.getPointZeroOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroOnes()
						.multiply(Constants.POINT_ZERO_ONE));
			}
			if (currencyStockDataTable.getPointZeroZeroFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroFives()
						.multiply(Constants.POINT_ZERO_ZERO_FIVE));
			}
			
			if (currencyStockDataTable.getPointZeroZeroOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroOnes()
						.multiply(Constants.POINT_ZERO_ZERO_ONE));
			}
			
			currencyStockDataTable.setModRowTotal(modifySubTotal);

		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}
	
	public void getReadOnly(CurrencyStockDataTable currencyStockDataTable) {

			if (currencyStockDataTable.getOneLakh() != null) {
				currencyStockDataTable.setBooOneLakhs(false);
			} else {
				currencyStockDataTable.setBooOneLakhs(true);
			}
			if (currencyStockDataTable.getFiftyThousand() != null) {
				currencyStockDataTable.setBooFiftyThousands(false);
			} else {
				currencyStockDataTable.setBooFiftyThousands(true);
			}
			if (currencyStockDataTable.getTwentyThousand() != null) {
				currencyStockDataTable.setBooTwentyThousands(false);
			} else {
				currencyStockDataTable.setBooTwentyThousands(true);
			}
			if (currencyStockDataTable.getTenThousand() != null) {
				currencyStockDataTable.setBooTenThousands(false);
			} else {
				currencyStockDataTable.setBooTenThousands(true);
			}
			if (currencyStockDataTable.getFiveThousand() != null) {
				currencyStockDataTable.setBooFiveThousands(false);
			} else {
				currencyStockDataTable.setBooFiveThousands(true);
			}
			if (currencyStockDataTable.getTwoThousand() != null) {
				currencyStockDataTable.setBooTwoThousands(false);
			} else {
				currencyStockDataTable.setBooTwoThousands(true);
			}
			if (currencyStockDataTable.getThousand() != null) {
				currencyStockDataTable.setBooThousands(false);
			} else {
				currencyStockDataTable.setBooThousands(true);
			}
			if (currencyStockDataTable.getFiveHundred() != null) {
				currencyStockDataTable.setBooFiveHundreds(false);
			} else {
				currencyStockDataTable.setBooFiveHundreds(true);
			}
			if (currencyStockDataTable.getTwoHundred() != null) {
				currencyStockDataTable.setBooTwoHundreds(false);
			} else {
				currencyStockDataTable.setBooTwoHundreds(true);
			}
			if (currencyStockDataTable.getHundred() != null) {
				currencyStockDataTable.setBooHundreds(false);
			} else {
				currencyStockDataTable.setBooHundreds(true);
			}
			if (currencyStockDataTable.getFifty() != null) {
				currencyStockDataTable.setBooFiftys(false);
			} else {
				currencyStockDataTable.setBooFiftys(true);
			}
			if (currencyStockDataTable.getTwenty() != null) {
				currencyStockDataTable.setBooTwentys(false);
			} else {
				currencyStockDataTable.setBooTwentys(true);
			}
			if (currencyStockDataTable.getTen() != null) {
				currencyStockDataTable.setBooTens(false);
			} else {
				currencyStockDataTable.setBooTens(true);
			}
			if (currencyStockDataTable.getFive() != null) {
				currencyStockDataTable.setBooFives(false);
			} else {
				currencyStockDataTable.setBooFives(true);
			}
			if (currencyStockDataTable.getTwo() != null) {
				currencyStockDataTable.setBooTwos(false);
			} else {
				currencyStockDataTable.setBooTwos(true);
			}
			if (currencyStockDataTable.getOne() != null) {
				currencyStockDataTable.setBooOnes(false);
			} else {
				currencyStockDataTable.setBooOnes(true);
			}
			if (currencyStockDataTable.getPointFive() != null) {
				currencyStockDataTable.setBooPointFives(false);
			} else {
				currencyStockDataTable.setBooPointFives(true);
			}
			if (currencyStockDataTable.getPointTwoFive() != null) {
				currencyStockDataTable.setBooPointTwoFives(false);
			} else {
				currencyStockDataTable.setBooPointTwoFives(true);
			}
			if (currencyStockDataTable.getPointOne() != null) {
				currencyStockDataTable.setBooPointOnes(false);
			} else {
				currencyStockDataTable.setBooPointOnes(true);
			}
			if (currencyStockDataTable.getPointZeroFive() != null) {
				currencyStockDataTable.setBooPointZeroFives(false);
			} else {
				currencyStockDataTable.setBooPointZeroFives(true);
			}
			if (currencyStockDataTable.getPointZeroTwo() != null) {
				currencyStockDataTable.setBooPointZeroTwos(false);
			} else {
				currencyStockDataTable.setBooPointZeroTwos(true);
			}
			if (currencyStockDataTable.getPointZeroOne() != null) {
				currencyStockDataTable.setBooPointZeroOnes(false);
			} else {
				currencyStockDataTable.setBooPointZeroOnes(true);
			}
			if (currencyStockDataTable.getPointZeroZeroFive() != null) {
				currencyStockDataTable.setBooPointZeroZeroFives(false);
			} else {
				currencyStockDataTable.setBooPointZeroZeroFives(true);
			}
			if (currencyStockDataTable.getPointZeroZeroOne() != null) {
				currencyStockDataTable.setBooPointZeroZeroOnes(false);
			} else {
				currencyStockDataTable.setBooPointZeroZeroOnes(true);
			}

	}
	
	public void tallyCashDetails(){
		
		currencyStockHeaderBeanList.clear();
		currencyStockDetailBeanList.clear();
		currencyStockReportBeanList.clear();
		
		CurrencyStockReportBean reportBean=null;
		CurrencyStockHeaderBean headerBean=null;
		CurrencyStockDetailBean detailBean=null;
		
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		
		String logoPath =null;
		if(sessionManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		
		
		
		BigDecimal systemNoteTolal=BigDecimal.ZERO;		
		BigDecimal systemAmountTolal=BigDecimal.ZERO;
		
		BigDecimal physicalNoteTolal=BigDecimal.ZERO;
		BigDecimal physicalAmountTolal=BigDecimal.ZERO;
		BigDecimal differenceAmountTolal=BigDecimal.ZERO;
		
		reportBean = new CurrencyStockReportBean();
		for(CurrencyStockDataTable currencyStockDataTable: currencyStockDataTableList){
			systemNoteTolal=BigDecimal.ZERO;		
			systemAmountTolal=BigDecimal.ZERO;			
			physicalNoteTolal=BigDecimal.ZERO;
			physicalAmountTolal=BigDecimal.ZERO;
			differenceAmountTolal=BigDecimal.ZERO;
			
			headerBean = new CurrencyStockHeaderBean();
			detailBean = new CurrencyStockDetailBean();
			
			if(currencyStockDataTable.getModRowTotal().compareTo(BigDecimal.ZERO)==0){
				currencyStockDataTable.setModRowTotal(currencyStockDataTable.getRowTotal());
			}
		
			boolean isTotal = currencyStockDataTable.getRowTotal().equals(currencyStockDataTable.getModRowTotal());
		
			if(isTotal==false){
			
				if(currencyStockDataTable.getOneLakh()!=null){					
					if(currencyStockDataTable.getOneLakhs().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setOneLakhs(currencyStockDataTable.getOneLakhs());
					}
					boolean status = currencyStockDataTable.getOneLakhs().equals(currencyStockDataTable.getOneLakh());
					if(status==false){
						detailBean.setOneLakhDenomId(currencyStockDataTable.getOneLakhDenomId());
						detailBean.setOneLakh(currencyStockDataTable.getOneLakh());
						detailBean.setOneLakhs(currencyStockDataTable.getOneLakhs());
						detailBean.setOneLakhDenomAmount(currencyStockDataTable.getOneLakhDenomAmount());
						detailBean.setOneLakhPNotes(currencyStockDataTable.getOneLakhPNotes());
						
						//systemNoteTolal = systemNoteTolal.add(currencyStockDataTable.geto)
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getOneLakhPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getOneLakhDenomAmount().multiply(currencyStockDataTable.getOneLakhPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getOneLakhs());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getOneLakhs());
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}			
				}
				if(currencyStockDataTable.getFiftyThousand()!=null){
					if(currencyStockDataTable.getFiftyThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setFiftyThousands(currencyStockDataTable.getFiftyThousands());
					}
					boolean status = currencyStockDataTable.getFiftyThousands().equals(currencyStockDataTable.getFiftyThousand());
					if(status==false){
						detailBean.setFiftyThousandDenomId(currencyStockDataTable.getFiftyThousandDenomId());
						detailBean.setFiftyThousands(currencyStockDataTable.getFiftyThousands());
						detailBean.setFiftyThousand(currencyStockDataTable.getFiftyThousand());
						detailBean.setFiftyThousandDenomAmount(currencyStockDataTable.getFiftyThousandDenomAmount());
						detailBean.setFiftyThousandPNotes(currencyStockDataTable.getFiftyThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getFiftyThousandPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getFiftyThousandDenomAmount().multiply(currencyStockDataTable.getFiftyThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getFiftyThousands());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getFiftyThousands().multiply(currencyStockDataTable.getFiftyThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
						
					}			
				}
				if(currencyStockDataTable.getTwentyThousand()!=null){
					if(currencyStockDataTable.getTwentyThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTwentyThousands(currencyStockDataTable.getTwentyThousands());
					}
					boolean status = currencyStockDataTable.getTwentyThousands().equals(currencyStockDataTable.getTwentyThousand());
					if(status==false){
						detailBean.setTwentyThousandDenomId(currencyStockDataTable.getFiftyThousandDenomId());
						detailBean.setTwentyThousands(currencyStockDataTable.getTwentyThousands());
						detailBean.setTwentyThousand(currencyStockDataTable.getTwentyThousand());
						detailBean.setTwentyThousandDenomAmount(currencyStockDataTable.getTwentyThousandDenomAmount());
						detailBean.setTwentyThousandPNotes(currencyStockDataTable.getTwentyThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTwentyThousandPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTwentyThousandDenomAmount().multiply(currencyStockDataTable.getTwentyThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwentyThousands().multiply(currencyStockDataTable.getTwentyThousandPNotes()));
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwentyThousands().multiply(currencyStockDataTable.getTwentyThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}			
				}
				if(currencyStockDataTable.getTenThousand()!=null){
					if(currencyStockDataTable.getTenThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTenThousands(currencyStockDataTable.getTenThousands());
					}
					boolean status = currencyStockDataTable.getTenThousands().equals(currencyStockDataTable.getTenThousand());
					if(status==false){
						detailBean.setTenThousandDenomId(currencyStockDataTable.getTenThousandDenomId());
						detailBean.setTenThousands(currencyStockDataTable.getTenThousands());
						detailBean.setTenThousand(currencyStockDataTable.getTenThousand());
						detailBean.setTenThousandDenomAmount(currencyStockDataTable.getTenThousandDenomAmount());
						detailBean.setTenThousandPNotes(currencyStockDataTable.getTenThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTenThousandPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTenThousandDenomAmount().multiply(currencyStockDataTable.getTenThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTenThousands());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTenThousands().multiply(currencyStockDataTable.getTenThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}			
				}
				if(currencyStockDataTable.getFiveThousand()!=null){
					if(currencyStockDataTable.getFiveThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setFiveThousands(currencyStockDataTable.getFiveThousands());
					}
					boolean status = currencyStockDataTable.getFiveThousands().equals(currencyStockDataTable.getFiveThousand());
					if(status==false){
						detailBean.setFiveThousandDenomId(currencyStockDataTable.getFiveThousandDenomId());
						detailBean.setFiveThousands(currencyStockDataTable.getFiveThousands());
						detailBean.setFiveThousand(currencyStockDataTable.getFiveThousand());
						detailBean.setFiveThousandDenomAmount(currencyStockDataTable.getFiveThousandDenomAmount());
						detailBean.setFiveThousandPNotes(currencyStockDataTable.getFiveThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getFiveThousandPNotes());
						
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getFiveThousandDenomAmount().multiply(currencyStockDataTable.getFiveThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getFiveThousands());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getFiveThousands().multiply(currencyStockDataTable.getFiveThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}			
				}
				if(currencyStockDataTable.getTwoThousand()!=null){
					if(currencyStockDataTable.getTwoThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTwoThousands(currencyStockDataTable.getTenThousands());
					}
					boolean status = currencyStockDataTable.getTwoThousands().equals(currencyStockDataTable.getTwoThousand());
					if(status==false){
						detailBean.setTwoThousandDenomId(currencyStockDataTable.getTwoThousandDenomId());
						detailBean.setTwoThousands(currencyStockDataTable.getTwoThousands());
						detailBean.setTwoThousand(currencyStockDataTable.getTwoThousand());
						detailBean.setTwoThousandDenomAmount(currencyStockDataTable.getTwoThousandDenomAmount());
						detailBean.setTwoThousandPNotes(currencyStockDataTable.getTwoThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTwoThousandPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTwoThousandDenomAmount().multiply(currencyStockDataTable.getTwoThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwoThousands());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwoThousands().multiply(currencyStockDataTable.getTwoThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}			
				}
				if(currencyStockDataTable.getThousand()!=null){
					if(currencyStockDataTable.getThousands().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setThousands(currencyStockDataTable.getThousand());
					}
					boolean status = currencyStockDataTable.getThousands().equals(currencyStockDataTable.getThousand());
					if(status==false){
						detailBean.setThousandDenomId(currencyStockDataTable.getThousandDenomId());
						detailBean.setThousands(currencyStockDataTable.getThousands());
						detailBean.setThousand(currencyStockDataTable.getThousand());
						detailBean.setThousandDenomAmount(currencyStockDataTable.getThousandDenomAmount());
						detailBean.setThousandPNotes(currencyStockDataTable.getThousandPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getThousandPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getThousandDenomAmount().multiply(currencyStockDataTable.getThousandPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getThousands());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getThousands().multiply(currencyStockDataTable.getThousandDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getFiveHundred()!=null){
					if(currencyStockDataTable.getFiveHundreds().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setFiveHundreds(currencyStockDataTable.getFiveHundred());
					}
					boolean status = currencyStockDataTable.getFiveHundreds().equals(currencyStockDataTable.getFiveHundred());
					if(status==false){
						detailBean.setFiveHundredDenomId(currencyStockDataTable.getThousandDenomId());
						detailBean.setFiveHundreds(currencyStockDataTable.getFiveHundreds());
						detailBean.setFiveHundred(currencyStockDataTable.getFiveHundred());
						detailBean.setFiveHundredDenomAmount(currencyStockDataTable.getFiveHundredDenomAmount());
						detailBean.setFiveHundredPNotes(currencyStockDataTable.getFiveHundredPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getFiveHundredPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getFiveHundredDenomAmount().multiply(currencyStockDataTable.getFiveHundredPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getFiveHundreds());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getFiveHundreds().multiply(currencyStockDataTable.getFiveHundredDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}				
				if(currencyStockDataTable.getTwoHundred()!=null){
					if(currencyStockDataTable.getTwoHundreds().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTwoHundreds(currencyStockDataTable.getTwoHundred());
					}
					boolean status = currencyStockDataTable.getTwoHundreds().equals(currencyStockDataTable.getTwoHundred());
					if(status==false){
						detailBean.setTwoHundredDenomId(currencyStockDataTable.getTwoHundredDenomId());
						detailBean.setTwoHundreds(currencyStockDataTable.getTwoHundreds());
						detailBean.setTwoHundred(currencyStockDataTable.getTwoHundred());
						detailBean.setTwoHundredDenomAmount(currencyStockDataTable.getTwoHundredDenomAmount());
						detailBean.setTwoHundredPNotes(currencyStockDataTable.getTwoHundredPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTwoHundredPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTwoHundredDenomAmount().multiply(currencyStockDataTable.getTwoHundredPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwoHundreds());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwoHundreds().multiply(currencyStockDataTable.getTwoHundredDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getHundred()!=null){
					if(currencyStockDataTable.getHundreds().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setHundreds(currencyStockDataTable.getHundred());
					}
					boolean status = currencyStockDataTable.getHundreds().equals(currencyStockDataTable.getHundred());
					if(status==false){
						detailBean.setHundredDenomId(currencyStockDataTable.getHundredDenomId());
						detailBean.setHundreds(currencyStockDataTable.getHundreds());
						detailBean.setHundred(currencyStockDataTable.getHundred());
						detailBean.setHundredDenomAmount(currencyStockDataTable.getHundredDenomAmount());
						detailBean.setHundredPNotes(currencyStockDataTable.getHundredPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getHundredPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getHundredDenomAmount().multiply(currencyStockDataTable.getHundredPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getHundreds());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getHundreds().multiply(currencyStockDataTable.getHundredDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getFifty()!=null){
					if(currencyStockDataTable.getFiftys().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setFiftys(currencyStockDataTable.getFifty());
					}
					boolean status = currencyStockDataTable.getFiftys().equals(currencyStockDataTable.getFifty());
					if(status==false){
						detailBean.setFiftyDenomId(currencyStockDataTable.getFiftyDenomId());
						detailBean.setFiftys(currencyStockDataTable.getFiftys());
						detailBean.setFifty(currencyStockDataTable.getFifty());
						detailBean.setFiftyDenomAmount(currencyStockDataTable.getFiftyDenomAmount());
						detailBean.setFiftyPNotes(currencyStockDataTable.getFiftyPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getFiftyPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getFiftyDenomAmount().multiply(currencyStockDataTable.getFiftyPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getFiftys());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getFiftys().multiply(currencyStockDataTable.getFiftyDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getTwenty()!=null){
					if(currencyStockDataTable.getTwentys().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTwentys(currencyStockDataTable.getTwenty());
					}
					boolean status = currencyStockDataTable.getTwentys().equals(currencyStockDataTable.getTwenty());
					if(status==false){
						detailBean.setTwentyDenomId(currencyStockDataTable.getTwentyDenomId());
						detailBean.setTwentys(currencyStockDataTable.getTwentys());
						detailBean.setTwenty(currencyStockDataTable.getTwenty());
						detailBean.setTwentyDenomAmount(currencyStockDataTable.getTwentyDenomAmount());
						detailBean.setTwentyPNotes(currencyStockDataTable.getTwentyPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTwentyPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTwentyDenomAmount().multiply(currencyStockDataTable.getTwentyPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwentys());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwentys().multiply(currencyStockDataTable.getTwentyDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getTen()!=null){
					if(currencyStockDataTable.getTens().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTens(currencyStockDataTable.getTen());
					}
					boolean status = currencyStockDataTable.getTens().equals(currencyStockDataTable.getTen());
					if(status==false){
						detailBean.setTenDenomId(currencyStockDataTable.getTenDenomId());
						detailBean.setTens(currencyStockDataTable.getTens());
						detailBean.setTen(currencyStockDataTable.getTen());
						detailBean.setTenDenomAmount(currencyStockDataTable.getTenDenomAmount());
						detailBean.setTenPNotes(currencyStockDataTable.getTenPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTenPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTenDenomAmount().multiply(currencyStockDataTable.getTenPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTens());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTens().multiply(currencyStockDataTable.getTenDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getFive()!=null){
					if(currencyStockDataTable.getFives().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setFives(currencyStockDataTable.getFive());
					}
					boolean status = currencyStockDataTable.getFives().equals(currencyStockDataTable.getFive());
					if(status==false){
						detailBean.setFiveDenomId(currencyStockDataTable.getFiveDenomId());
						detailBean.setFives(currencyStockDataTable.getFives());
						detailBean.setFive(currencyStockDataTable.getFive());
						detailBean.setFiveDenomAmount(currencyStockDataTable.getFiveDenomAmount());
						detailBean.setFivePNotes(currencyStockDataTable.getFivePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getFivePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getFiveDenomAmount().multiply(currencyStockDataTable.getFivePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getFives());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getFives().multiply(currencyStockDataTable.getFiveDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getTwo()!=null){
					if(currencyStockDataTable.getTwos().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setTwos(currencyStockDataTable.getTwo());
					}
					boolean status = currencyStockDataTable.getTwos().equals(currencyStockDataTable.getTwo());
					if(status==false){
						detailBean.setTwoDenomId(currencyStockDataTable.getTwoDenomId());
						detailBean.setTwos(currencyStockDataTable.getTwos());
						detailBean.setTwo(currencyStockDataTable.getTwo());
						detailBean.setTwoDenomAmount(currencyStockDataTable.getTwoDenomAmount());
						detailBean.setTwoPNotes(currencyStockDataTable.getTwoPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getTwoPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getTwoDenomAmount().multiply(currencyStockDataTable.getTwoPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwos());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwos().multiply(currencyStockDataTable.getTwoDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getOne()!=null){
					if(currencyStockDataTable.getOnes().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setOnes(currencyStockDataTable.getOne());
					}
					boolean status = currencyStockDataTable.getOnes().equals(currencyStockDataTable.getOne());
					if(status==false){
						detailBean.setOneDenomId(currencyStockDataTable.getOneDenomId());
						detailBean.setOnes(currencyStockDataTable.getTwos());
						detailBean.setOne(currencyStockDataTable.getTwo());
						detailBean.setOneDenomAmount(currencyStockDataTable.getOneDenomAmount());
						detailBean.setOnePNotes(currencyStockDataTable.getOnePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getOnePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getOneDenomAmount().multiply(currencyStockDataTable.getOnePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getTwos());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getTwos().multiply(currencyStockDataTable.getOneDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointFive()!=null){
					if(currencyStockDataTable.getPointFives().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointFives(currencyStockDataTable.getPointFive());
					}
					boolean status = currencyStockDataTable.getPointFives().equals(currencyStockDataTable.getPointFive());
					if(status==false){
						detailBean.setPointFiveDenomId(currencyStockDataTable.getPointFiveDenomId());
						detailBean.setPointFives(currencyStockDataTable.getPointFives());
						detailBean.setPointFive(currencyStockDataTable.getPointFive());
						detailBean.setPointFiveDenomAmount(currencyStockDataTable.getPointFiveDenomAmount());
						detailBean.setPointFivePNotes(currencyStockDataTable.getPointFivePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointFivePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointFiveDenomAmount().multiply(currencyStockDataTable.getPointFivePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointFives());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointFives().multiply(currencyStockDataTable.getPointFiveDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointTwoFive()!=null){
					if(currencyStockDataTable.getPointTwoFives().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointTwoFives(currencyStockDataTable.getPointTwoFive());
					}
					boolean status = currencyStockDataTable.getPointTwoFives().equals(currencyStockDataTable.getPointTwoFive());
					if(status==false){
						detailBean.setPointTwoFiveDenomId(currencyStockDataTable.getPointTwoFiveDenomId());
						detailBean.setPointTwoFives(currencyStockDataTable.getPointTwoFives());
						detailBean.setPointTwoFive(currencyStockDataTable.getPointTwoFive());
						detailBean.setPointTwoFiveDenomAmount(currencyStockDataTable.getPointTwoFiveDenomAmount());
						detailBean.setPointTwoFivePNotes(currencyStockDataTable.getPointTwoFivePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointTwoFivePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointTwoFiveDenomAmount().multiply(currencyStockDataTable.getPointTwoFivePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointTwoFives());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointTwoFives().multiply(currencyStockDataTable.getPointTwoFiveDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointOne()!=null){
					if(currencyStockDataTable.getPointOnes().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointOnes(currencyStockDataTable.getPointOne());
					}
					boolean status = currencyStockDataTable.getPointOnes().equals(currencyStockDataTable.getPointOne());
					if(status==false){
						detailBean.setPointOneDenomId(currencyStockDataTable.getPointOneDenomId());
						detailBean.setPointOnes(currencyStockDataTable.getPointOnes());
						detailBean.setPointOne(currencyStockDataTable.getPointOne());
						detailBean.setPointOneDenomAmount(currencyStockDataTable.getPointOneDenomAmount());
						detailBean.setPointOnePNotes(currencyStockDataTable.getPointOnePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointOnePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointOneDenomAmount().multiply(currencyStockDataTable.getPointOnePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointOnes());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointOnes().multiply(currencyStockDataTable.getPointOneDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointZeroFive()!=null){
					if(currencyStockDataTable.getPointZeroFives().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointZeroFives(currencyStockDataTable.getPointZeroFive());
					}
					boolean status = currencyStockDataTable.getPointZeroFives().equals(currencyStockDataTable.getPointZeroFive());
					if(status==false){
						detailBean.setPointZeroFiveDenomId(currencyStockDataTable.getPointZeroFiveDenomId());
						detailBean.setPointZeroFives(currencyStockDataTable.getPointZeroFives());
						detailBean.setPointZeroFive(currencyStockDataTable.getPointZeroFive());
						detailBean.setPointZeroFiveDenomAmount(currencyStockDataTable.getPointZeroFiveDenomAmount());
						detailBean.setPointZeroFivePNotes(currencyStockDataTable.getPointZeroFivePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointZeroFivePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointZeroFiveDenomAmount().multiply(currencyStockDataTable.getPointZeroFivePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointZeroFives());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointZeroFives().multiply(currencyStockDataTable.getPointZeroFiveDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointZeroTwo()!=null){
					if(currencyStockDataTable.getPointZeroTwos().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointZeroTwos(currencyStockDataTable.getPointZeroTwo());
					}
					boolean status = currencyStockDataTable.getPointZeroTwos().equals(currencyStockDataTable.getPointZeroTwo());
					if(status==false){
						detailBean.setPointZeroTwoDenomId(currencyStockDataTable.getPointZeroTwoDenomId());
						detailBean.setPointZeroTwos(currencyStockDataTable.getPointZeroTwos());
						detailBean.setPointZeroTwo(currencyStockDataTable.getPointZeroTwo());
						detailBean.setPointZeroTwoDenomAmount(currencyStockDataTable.getPointZeroTwoDenomAmount());
						detailBean.setPointZeroTwoPNotes(currencyStockDataTable.getPointZeroTwoPNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointZeroTwoPNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointZeroTwoDenomAmount().multiply(currencyStockDataTable.getPointZeroTwoPNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointZeroTwos());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointZeroTwos().multiply(currencyStockDataTable.getPointZeroTwoDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointZeroOne()!=null){
					if(currencyStockDataTable.getPointZeroOnes().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointZeroOnes(currencyStockDataTable.getPointZeroOne());
					}
					boolean status = currencyStockDataTable.getPointZeroOnes().equals(currencyStockDataTable.getPointZeroOne());
					if(status==false){
						detailBean.setPointZeroOneDenomId(currencyStockDataTable.getPointZeroOneDenomId());
						detailBean.setPointZeroOnes(currencyStockDataTable.getPointZeroOnes());
						detailBean.setPointZeroOne(currencyStockDataTable.getPointZeroOne());
						detailBean.setPointZeroOneDenomAmount(currencyStockDataTable.getPointZeroOneDenomAmount());
						detailBean.setPointZeroOnePNotes(currencyStockDataTable.getPointZeroOnePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointZeroOnePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointZeroOneDenomAmount().multiply(currencyStockDataTable.getPointZeroOnePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointZeroOnes());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointZeroOnes().multiply(currencyStockDataTable.getPointZeroOneDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointZeroZeroFive()!=null){
					if(currencyStockDataTable.getPointZeroZeroFives().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointZeroZeroFives(currencyStockDataTable.getPointZeroZeroFive());
					}
					boolean status = currencyStockDataTable.getPointZeroZeroFives().equals(currencyStockDataTable.getPointZeroZeroFive());
					if(status==false){
						detailBean.setPointZeroZeroFiveDenomId(currencyStockDataTable.getPointZeroOneDenomId());
						detailBean.setPointZeroZeroFives(currencyStockDataTable.getPointZeroZeroFives());
						detailBean.setPointZeroZeroFive(currencyStockDataTable.getPointZeroZeroFive());
						detailBean.setPointZeroZeroFiveDenomAmount(currencyStockDataTable.getPointZeroZeroFiveDenomAmount());
						detailBean.setPointZeroZeroFivePNotes(currencyStockDataTable.getPointZeroZeroFivePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointZeroZeroFivePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointZeroZeroFiveDenomAmount().multiply(currencyStockDataTable.getPointZeroZeroFivePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointZeroZeroFives());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointZeroZeroFives().multiply(currencyStockDataTable.getPointZeroZeroFiveDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}
				}
				if(currencyStockDataTable.getPointZeroZeroOne()!=null){
					if(currencyStockDataTable.getPointZeroZeroOnes().compareTo(BigDecimal.ZERO)==0){
						currencyStockDataTable.setPointZeroZeroOnes(currencyStockDataTable.getPointZeroZeroOne());
					}
					boolean status = currencyStockDataTable.getPointZeroZeroOnes().equals(currencyStockDataTable.getPointZeroZeroOne());
					if(status==false){
						detailBean.setPointZeroZeroOneDenomId(currencyStockDataTable.getPointZeroOneDenomId());
						detailBean.setPointZeroZeroOnes(currencyStockDataTable.getPointZeroZeroOnes());
						detailBean.setPointZeroZeroOne(currencyStockDataTable.getPointZeroZeroOne());
						detailBean.setPointZeroZeroOneDenomAmount(currencyStockDataTable.getPointZeroZeroOneDenomAmount());
						detailBean.setPointZeroZeroOnePNotes(currencyStockDataTable.getPointZeroZeroOnePNotes());
						systemNoteTolal=systemNoteTolal.add(currencyStockDataTable.getPointZeroZeroOnePNotes());
						systemAmountTolal=systemAmountTolal.add(currencyStockDataTable.getPointZeroZeroOneDenomAmount().multiply(currencyStockDataTable.getPointZeroZeroOnePNotes()));
						physicalNoteTolal = physicalNoteTolal.add(currencyStockDataTable.getPointZeroZeroOnes());
						physicalAmountTolal = physicalAmountTolal.add(currencyStockDataTable.getPointZeroZeroOnes().multiply(currencyStockDataTable.getPointZeroZeroOneDenomAmount()));
						differenceAmountTolal=differenceAmountTolal.add(systemAmountTolal.subtract(physicalAmountTolal));
					}					
				}				
				headerBean.setCurrencyName(currencyStockDataTable.getCurrencyName());
				headerBean.setSystemCash(systemAmountTolal);
				headerBean.setPhysicalCash(physicalAmountTolal);
				headerBean.setDifferenceCash(headerBean.getSystemCash().subtract(headerBean.getPhysicalCash()));				
				
				detailBean.setSystemNoteTotal(systemNoteTolal);
				detailBean.setSystemAmountTotal(systemAmountTolal);
				detailBean.setPhysicalNoteTotal(physicalNoteTolal);
				detailBean.setPhysicalAmountTotal(physicalAmountTolal);
				detailBean.setDiffAmountTotal(systemAmountTolal.subtract(physicalAmountTolal));
				
				currencyStockHeaderBeanList.add(headerBean);
				currencyStockDetailBeanList.add(detailBean);
			}
		}		
		
		List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		
		reportBean.setBranchName(branchList.get(0).getBranchName());
		reportBean.setCashierName(sessionManage.getUserName());
		reportBean.setLogoPath(logoPath);			
		reportBean.setCurrencyStockHeaderBeanList(currencyStockHeaderBeanList);
		reportBean.setCurrencyStockDetailBeanList(currencyStockDetailBeanList);
		
		currencyStockReportBeanList.add(reportBean);		
	}
	
	JasperPrint jasperPrint;  
	public void init() throws JRException{ 		
		tallyCashDetails();
		JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(currencyStockReportBeanList);  
		String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/CashTallyReport.jasper");   
		jasperPrint=JasperFillManager.fillReport(reportPath,getMap(),beanCollectionDataSource);  
		//for Direct print 
		//JasperPrintManager jasperPrintManager = new JasperPrintManager();
		//JasperPrintManager.printReport(jasperPrint, true);
		//JasperPrintManager.printPage(jasperPrint, 1, true);
		
	}
	
	public void generateNewUpdatedReports(ActionEvent actionEvent) throws JRException, IOException{
		String errmsg = saveInfoToDb();
		if(errmsg!=null){
			setWarningMessage("Exception : " + errmsg);
			RequestContext.getCurrentInstance().execute(
					"proceErr.show();");
			return;
		}else{
			
			if(currencyStockDetailBeanList.size()>0){
				init();  
				HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
				httpServletResponse.addHeader("Content-disposition", "attachment; filename=CashDepositReport.pdf");  
				ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
				JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
				FacesContext.getCurrentInstance().responseComplete();
			}			
			
			/*HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=CashDepositReport.pdf");  
			ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
			JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
			FacesContext.getCurrentInstance().responseComplete();
			
			
			PrintService[] services  = null;
			PrintService   myPrinter = null;
			DocFlavor myFlavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
			PrintRequestAttributeSet jobAttrs = new HashPrintRequestAttributeSet();
			services = PrintServiceLookup.lookupPrintServices( myFlavor, jobAttrs );
			if ( System.getProperty( "os.name" ).startsWith( "Windows" ) ){
			    // No problem under Windows ...
			    myPrinter = PrintServiceLookup.lookupDefaultPrintService();
			}
			
			
			PrinterJob printerJob = PrinterJob.getPrinterJob();


	        PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
	        printerJob.defaultPage(pageFormat);

	        int selectedService = 0;
	        
	        String printerNameShort="Nitro PDF Creater (Pro8)";

	        AttributeSet attributeSet = new HashPrintServiceAttributeSet(new PrinterName(printerNameShort, null));


	        PrintService[] printService = PrintServiceLookup.lookupPrintServices(null, attributeSet);
	        
	        PrintService myService = null;
	        for (PrintService printService1 : printService) {
	            if (printService1.getName().equals("Nitro PDF Creater (Pro 8)")) {
	                myService = printService1; 
	                break;
	            }
	        }
	        
	        System.out.println(myService);
			
			JRPrintServiceExporter exporter;
	        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
	        printRequestAttributeSet.add(new Copies(1));
			
			// these are deprecated
	        exporter = new JRPrintServiceExporter();
	        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, myPrinter);
	        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, myPrinter.getAttributes());
	        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
	        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.TRUE);
	        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
*/
		}
	
	}
	
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	
	private List<UserFinancialYear> financialYearList = new ArrayList<UserFinancialYear>();
	// for carrying object for transaction SAVE
		List<CashDetails> cashDetailList = new ArrayList<CashDetails>();
		List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();
	private BigDecimal lineNo = BigDecimal.ZERO;
	private BigDecimal companyCode;
	
	// for getting financial year
		public int getFinaceYear() {
			int finaceYear = 0;
			try {

				financialYearList = specialCustomerDealRequestService
						.getUserFinancialYear(new Date());
				log.info("financialYearList :" + financialYearList.size());
				if (financialYearList != null)
					finaceYear = Integer.parseInt(financialYearList.get(0)
							.getFinancialYear().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return finaceYear;
		}

		
	public CashHeader saveCashHeader(BigDecimal documentNumber) {
		CashHeader cashHeader = new CashHeader();
		CountryMaster appCountry = new CountryMaster();
		appCountry.setCountryId(sessionManage.getCountryId()); // country from
																// session based
																// on country
																// login
		cashHeader.setApplicationCountryId(appCountry);

		CompanyMaster cmpMaster = new CompanyMaster();
		cmpMaster.setCompanyId(sessionManage.getCompanyId()); // company from
																// session based
																// on company
																// login
		cashHeader.setCompanyId(cmpMaster);

		Document document = new Document();
		document.setDocumentID(specialCustomerDealRequestService
				.getDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER)));
		cashHeader.setDocumentId(document);

		cashHeader.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER));
		cashHeader.setFinancialYear(new BigDecimal(getFinaceYear()));

		cashHeader.setDocumentNo(documentNumber);

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId())); // country Branch from Session
		cashHeader.setCountryBranchId(countryBranch);
		List<CountryBranch> branchList = cashTransferLToLService
				.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		cashHeader.setCountryBranchCode(branchList.get(0).getBranchId());
		cashHeader.setDocumentDate(new Date());
		cashHeader.setFromCashier(getFromCashier());
		cashHeader.setToCashier(getToCashier());
		//cashHeader.setToCountryBranchCode(getToLocation());
		cashHeader.setIsActive(Constants.U);
		cashHeader.setCreatedBy(sessionManage.getUserName()); // user Name from
																// session
		cashHeader.setCreatedDate(new Date());

		return cashHeader;
	}
	
	public ForeignCurrencyAdjust saveforeignCurrencyAdjust(
			CashHeader cashHeader, BigDecimal currencyId,
			BigDecimal adjustmentAmount, BigDecimal notesQuantity,
			BigDecimal denominationId, BigDecimal documentNumber, Date acc_Month) {
		lineNo = lineNo.add(BigDecimal.ONE);
		ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();

		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(new BigDecimal(sessionManage
				.getSessionValue("companyId")));
		foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(new BigDecimal(sessionManage
				.getSessionValue("countryId")));
		foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

		foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER));
		foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(
				getFinaceYear()));
		foreignCurrencyAdjust.setDocumentNo(documentNumber);
		foreignCurrencyAdjust.setDocumentDate(new Date());

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId()));
		foreignCurrencyAdjust.setCountryBranch(countryBranch);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(currencyId);
		foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

		foreignCurrencyAdjust.setAdjustmentAmount(adjustmentAmount);
		foreignCurrencyAdjust.setNotesQuantity(notesQuantity);

		CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
		denominationMaster.setDenominationId(denominationId);
		foreignCurrencyAdjust.setFsDenominationId(denominationMaster);
		foreignCurrencyAdjust.setDocumentLineNumber(lineNo);
		foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

		foreignCurrencyAdjust.setProgNumber(Constants.CASH_TRANSFER_L_TO_L);
		// foreignCurrencyAdjust.setTransactionType(Constants.CT);
		// Modified "T" to Transfer stock T - F and R - C
		foreignCurrencyAdjust.setTransactionType(Constants.T);
		foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
		foreignCurrencyAdjust.setOracleUser(Constants.SAFE);
		// foreignCurrencyAdjust.setStockUpdated(Constants.Yes);
		foreignCurrencyAdjust.setDenaminationAmount(denominationMap
				.get(denominationId));
		foreignCurrencyAdjust.setCreatedDate(new Date());
		foreignCurrencyAdjust.setCreatedBy(sessionManage
				.getSessionValue("userName"));

		foreignCurrencyAdjust.setDocumentId(cashHeader.getDocumentId()
				.getDocumentID());

		foreignCurrencyAdjust.setCompanyCode(getCompanyCode());

		return foreignCurrencyAdjust;

	}
	
	public CashDetails saveCashDetail(CurrencyStockDataTable cashTransferDTObj,
			CashHeader cashHeader, BigDecimal documentNumber, int i) {
		CashDetails cashDetails = new CashDetails();
		cashDetails.setCashHeaderId(cashHeader); // to set cash header primary
													// key

		CountryMaster appCountry = new CountryMaster();
		appCountry.setCountryId(sessionManage.getCountryId()); // country from
																// session based
																// on country
																// login
		cashDetails.setApplicationCountryId(appCountry);

		CompanyMaster cmpMaster = new CompanyMaster();
		cmpMaster.setCompanyId(sessionManage.getCompanyId()); // company from
																// session based
																// on company
																// login
		cashDetails.setCompanyId(cmpMaster);

		Document document = new Document();
		document.setDocumentID(specialCustomerDealRequestService
				.getDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER)));
		cashDetails.setDocumentId(document);

		cashDetails.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER));

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(cashTransferDTObj.getCurrencyId());
		cashDetails.setCurrencyId(currencyMaster);

		UserFinancialYear financialYear = new UserFinancialYear();
		financialYear.setFinancialYearID(new BigDecimal(getFinaceYear()));
		cashDetails.setDocumentFinancialYear(financialYear);

		cashDetails.setDocumentNo(documentNumber);

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId())); // country Branch from Session
		cashDetails.setCountryBranchId(countryBranch);
		List<CountryBranch> branchList = cashTransferLToLService
				.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		cashDetails.setCountryBranchCode(branchList.get(0).getBranchId());
		cashDetails.setDocumentDate(new Date());
		cashDetails.setTotalValue(cashTransferDTObj.getModRowTotal()); // to set
																		// total
																		// transfer
																		// amount
																		// which
																		// is be
																		// sent

		cashDetails.setCreatedBy(sessionManage.getUserName()); // user Name from
																// session
		cashDetails.setCreatedDate(new Date());

		cashDetails.setIsActive(Constants.U);

		cashDetails.setDocumentLineNo(new BigDecimal(i));

		return cashDetails;
	}
	
	public String saveInfoToDb() {
		String errmsg=null;
		try{			
				for (CurrencyStockDataTable cashTransferDTObj : currencyStockDataTableList) {						
								
					if (cashTransferDTObj.getOneLakhs() != null &&  cashTransferDTObj.getOneLakhs().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getFiftyThousands()!= null &&  cashTransferDTObj.getFiftyThousands().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTwentyThousands()!= null &&  cashTransferDTObj.getTwentyThousands().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTenThousands()!= null && cashTransferDTObj.getTenThousands().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getFiveThousands()!= null && cashTransferDTObj.getFiveThousands().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTwoThousands()!= null &&  cashTransferDTObj.getTwoThousands().compareTo(BigDecimal.ZERO)!=0
							||cashTransferDTObj.getThousands()!= null &&  cashTransferDTObj.getThousands().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getFiveHundreds()!= null &&  cashTransferDTObj.getFiveHundreds().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTwoHundreds()!= null &&  cashTransferDTObj.getTwoHundreds().compareTo(BigDecimal.ZERO)!=0
							||cashTransferDTObj.getHundreds()!= null &&  cashTransferDTObj.getHundreds().compareTo(BigDecimal.ZERO)!=0
							||cashTransferDTObj.getFiftys()!= null &&  cashTransferDTObj.getFiftys().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTwentys()!= null &&  cashTransferDTObj.getTwentys().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getTens()!= null &&  cashTransferDTObj.getTens().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getFives()!= null &&  cashTransferDTObj.getFives().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getOnes()!= null &&  cashTransferDTObj.getOnes().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointFives()!= null &&  cashTransferDTObj.getPointFives().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointTwoFives()!= null &&  cashTransferDTObj.getPointTwoFives().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointOnes()!= null &&  cashTransferDTObj.getPointOnes().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointZeroFives()!= null &&  cashTransferDTObj.getPointZeroFives().compareTo(BigDecimal.ZERO)!=0
							||cashTransferDTObj.getPointZeroTwos()!= null &&  cashTransferDTObj.getPointZeroTwos().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointZeroOnes()!= null &&  cashTransferDTObj.getPointZeroOnes().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointZeroZeroFives()!= null &&  cashTransferDTObj.getPointZeroZeroFives().compareTo(BigDecimal.ZERO)!=0
							|| cashTransferDTObj.getPointZeroZeroOnes()!= null &&  cashTransferDTObj.getPointZeroZeroOnes().compareTo(BigDecimal.ZERO)!=0 ) {
												
							errmsg = cashTransferLToLService
									.updateCashdeposit(sessionManage.getCountryId(),
											sessionManage.getCompanyId(),
											sessionManage.getUserName(),
											new BigDecimal(sessionManage.getBranchId()),
											cashTransferDTObj
											.getCurrencyId());
								if(errmsg!=null){
									setWarningMessage("Exception : " + errmsg);
									RequestContext.getCurrentInstance().execute(
											"proceErr.show();");
									
								}					
							}						
						}
		}catch(Exception e) {
			setWarningMessage("Exception : " + errmsg);
			RequestContext.getCurrentInstance().execute(
					"proceErr.show();");
			
		} 
		return errmsg;
	}
	
	private int documentId = Integer
			.parseInt(Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER);

	public String getDocumentSerialID(String processIn) {

		String documentSerialId = "";

		log.info("process in :" + processIn);
		try {
			HashMap<String, String> outPutValues = generalService
					.getNextDocumentRefNumber(Integer.parseInt(sessionManage
							.getSessionValue("countryId")), Integer
							.parseInt(sessionManage
									.getSessionValue("companyId")), documentId,
							getFinaceYear(), processIn, sessionManage
									.getCountryBranchCode());

			String proceErrorMsg = outPutValues.get("PROCE_ERORRMSG");
			if (proceErrorMsg != null) {
				setWarningMessage(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				documentSerialId = outPutValues.get("DOCNO");
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
		return documentSerialId;

		/*
		 * String documentSerialID =
		 * specialCustomerDealRequestService.getNextDocumentSerialNumber
		 * (Integer.parseInt(sessionManage.getSessionValue("countryId")),
		 * Integer.parseInt(sessionManage.getSessionValue("companyId")),
		 * documentId, getFinaceYear(), processIn); return documentSerialID;
		 */
	}
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH))
				+ "/" + year;
	}
	public void generateReport1(String reportname,
			JRBeanCollectionDataSource beanCollectionDataSource)
			throws JRException {
		String reportDir = "D:\\\\easyBanker\\\\src\\\\Report\\\\";
		String reportDir2 = "H:/PrintCheck";
		String printer = "Nitro PDF Creator (Pro 8)";

		PrintService service = null;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		// aset.add(MediaSizeName.ISO_A4);
		PrintService[] services3 = PrintServiceLookup.lookupPrintServices(null,
				null);
		
		
		
		 DocFlavor psInFormat = DocFlavor.INPUT_STREAM.PDF;
		
		 PrintRequestAttributeSet aset1 = 
	               new HashPrintRequestAttributeSet();
	          aset.add(new PrinterName("\\\\122.166.97.138//Canon iP1300", null));
	          //aset.add(new Copies(5));
	          aset.add( MediaSizeName.ISO_A4 );
	          aset.add(Sides.DUPLEX);
	          PrintService[] services = 
	          PrintServiceLookup.lookupPrintServices(psInFormat, aset);
		
		
		

		for (int k = 0; k < services3.length; k++) {
			if (services3[k].getName().trim().equals(printer.trim())) {
				service = services3[k];
			}
		}
		// System.out.println("AAAAAAAAA"+service);

		if (service == null) {
			System.out.println("PRINTER NOT FOUND->" + printer);
		} else {

			System.out.println("AAAAAAAAA");
			JasperReport jr = JasperCompileManager.compileReport(reportname);
			JasperPrint jp = JasperFillManager.fillReport(jr, null,
					beanCollectionDataSource);

			JRExporter exporter = new JRPrintServiceExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
					service.getAttributes());
			exporter.setParameter(
					JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
					service.getAttributes());
			exporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG,
					Boolean.FALSE);
			exporter.setParameter(
					JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG,
					Boolean.TRUE);
			exporter.exportReport();

		}

	}

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	public List<CountryBranch> getLstCountryBranchForLocation() {
		return lstCountryBranchForLocation;
	}

	public void setLstCountryBranchForLocation(
			List<CountryBranch> lstCountryBranchForLocation) {
		this.lstCountryBranchForLocation = lstCountryBranchForLocation;
	}

	public List<CountryBranch> getLstCountryBranchToLocation() {
		return lstCountryBranchToLocation;
	}

	public void setLstCountryBranchToLocation(
			List<CountryBranch> lstCountryBranchToLocation) {
		this.lstCountryBranchToLocation = lstCountryBranchToLocation;
	}

	public List<CurrencyStockDataTable> getCurrencyStockDataTableList() {
		return currencyStockDataTableList;
	}

	public void setCurrencyStockDataTableList(
			List<CurrencyStockDataTable> currencyStockDataTableList) {
		this.currencyStockDataTableList = currencyStockDataTableList;
	}

	public List<BigDecimal> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<BigDecimal> currencyList) {
		this.currencyList = currencyList;
	}

	public boolean isBooNilCurrency() {
		return booNilCurrency;
	}

	public void setBooNilCurrency(boolean booNilCurrency) {
		this.booNilCurrency = booNilCurrency;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getToCashier() {
		return toCashier;
	}

	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
	}

	public String getToCashierName() {
		return toCashierName;
	}

	public void setToCashierName(String toCashierName) {
		this.toCashierName = toCashierName;
	}

	public String getTransferOption() {
		return transferOption;
	}

	public void setTransferOption(String transferOption) {
		this.transferOption = transferOption;
	}

	public BigDecimal getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(BigDecimal fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getFromLocationFullName() {
		return fromLocationFullName;
	}

	public void setFromLocationFullName(String fromLocationFullName) {
		this.fromLocationFullName = fromLocationFullName;
	}

	public String getFromCashier() {
		return fromCashier;
	}

	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	
	public void print(FileInputStream fis){
		FileInputStream psStream = null;
        try {
            psStream = new FileInputStream("c:\\myfile.pdf");
            } catch (FileNotFoundException ffne) {
              ffne.printStackTrace();
            }
            if (psStream == null) {
                return;
            }
        DocFlavor psInFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc myDoc = new SimpleDoc(psStream, psInFormat, null);  
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(psInFormat, aset);
         
        // this step is necessary because I have several printers configured
        PrintService myPrinter = null;
        for (int i = 0; i < services.length; i++){
        	String svcName = services[i].toString();       
            System.out.println("service found: "+svcName);
                
            if (svcName.contains("printer closest to me")){
                myPrinter = services[i];
                System.out.println("my printer found: "+svcName);
                break;
            }
        }
         
        if (myPrinter != null) {            
            DocPrintJob job = myPrinter.createPrintJob();
            try {
            job.print(myDoc, aset);
             
            } catch (Exception pe) {pe.printStackTrace();}
        } else {
            System.out.println("no printer services found");
        }
	}

}
