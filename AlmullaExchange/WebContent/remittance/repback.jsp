<%@ page language="java" session="true"%>
<%@page import="java.sql.*"%>

<%
	// Test repback.jsp
	String paymentId   = null;
    String paymentId1   = request.getParameter("paymentId"); //paymentId
    String paymentId2  = request.getParameter("PaymentID"); //PaymentID
    String paymentId3   = request.getParameter("paymentid"); //paymentid
    String ErrorNo     = request.getParameter("Error");
    String udf1        = request.getParameter("udf1");
    String udf2        = request.getParameter("udf2");
    String udf3        = request.getParameter("udf3");
    String udf4        = request.getParameter("udf4");
    String udf5        = request.getParameter("udf5");
    
    out.println(paymentId1+ "------" +paymentId2 );

    if (paymentId1 != null) {
    	paymentId=paymentId1;
    }else if (paymentId2 != null){
    	paymentId=paymentId2;
    }else{
    	paymentId=paymentId3;
    }

    String    result = null;
    String    postdate = null;
    String    tranid   = null;
    String    auth     = null;
    String    trackid  = null;
    String    ref = null;
    String    ErrorText = null;
    String    url = null;
    
    result = request.getParameter("result"); // original process
    
    // result = "CAPTURED";  // hard code Data to proceed
    //result = "CANCELED";  // hard code Data to proceed
    
      if (result!=null && result.equalsIgnoreCase("CAPTURED"))  {
    	  
         	// original process
            postdate = request.getParameter("postdate");
            tranid   = request.getParameter("tranid");
            auth     = request.getParameter("auth");
            trackid  = request.getParameter("trackid");
            ref      = request.getParameter("ref");
            
           /*  // hard code Data to proceed
            result   = "CAPTURED";
            postdate = "24-08-2015";
            tranid   = "24082015456";
            auth     = "KANMANI1";
            trackid  = "11223344";
            ref      = "2341423423"; */
            
            out.println("REDIRECT=https://applications2.almullagroup.com/AlmullaExchangeService/remittance/knet_success.xhtml?paymentId="+paymentId+"&result="+result+"&auth="+auth+"&ref="+ref+"&postdate="+postdate+"&trackid="+trackid+"&tranid="+tranid+"&udf1="+udf1+"&udf2="+udf2+"&udf3="+udf3+"&udf4="+udf4+"&udf5="+udf5);
            
            // hard code IP ADDRESS Data to proceed 
            try {
           	// response.sendRedirect("http://10.200.4.64:8085/AlmullaExchangeService/remittance/knet_success.xhtml?paymentId="+paymentId+"&result="+result+"&auth="+auth+"&ref="+ref+"&postdate="+postdate+"&trackid="+trackid+"&tranid="+tranid+"&udf1="+udf1+"&udf2="+udf2+"&udf3="+udf3+"&udf4="+udf4+"&udf5="+udf5);
            }catch (Exception e) {
    			e.printStackTrace();
    		}

        }else if(result!=null && result.equalsIgnoreCase("CANCELED")) {
        	
            postdate = request.getParameter("postdate");
            tranid   = request.getParameter("tranid");
            auth     = request.getParameter("auth");
            trackid  = request.getParameter("trackid");
            ref      = request.getParameter("ref");
            
           /*  // hard code Data to proceed
            result   = "CANCELED";
            postdate = "24-08-2015";
            tranid   = "24082015456";
            auth     = "KANMANI1";
            trackid  = "11223344";
            ref      = "2341423423"; */
        	
		    out.println("REDIRECT=https://applications2.almullagroup.com/AlmullaExchangeService/remittance/ErrorPage.xhtml?paymentId="+paymentId+"&result="+result+"&auth="+auth+"&ref="+ref+"&postdate="+postdate+"&trackid="+trackid+"&tranid="+tranid+"&udf1="+udf1+"&udf2="+udf2+"&udf3="+udf3+"&udf4="+udf4+"&udf5="+udf5);
		    
		    //remittance
		    // hard code IP ADDRESS Data to proceed 
		    try {
           	 //response.sendRedirect("http://10.200.4.64:8085/AlmullaExchangeService/remittance/ErrorPage.xhtml?paymentId="+paymentId+"&result="+result+"&ErrorText="+ErrorText);
            }catch (Exception e) {
    			e.printStackTrace();
    		}

        } else {    
        	
            postdate = request.getParameter("postdate");
            tranid   = request.getParameter("tranid");
            auth     = request.getParameter("auth");
            trackid  = request.getParameter("trackid");
            ref      = request.getParameter("ref");
            
            /* // hard code Data to proceed
            result   = "CANCELED";
            postdate = "24-08-2015";
            tranid   = "24082015456";
            auth     = "KANMANI1";
            trackid  = "11223344";
            ref      = "2341423423"; */

            out.println("REDIRECT=https://applications2.almullagroup.com/AlmullaExchangeService/remittance/ErrorPage.xhtml?paymentId="+paymentId+"&result="+result+"&auth="+auth+"&ref="+ref+"&postdate="+postdate+"&trackid="+trackid+"&tranid="+tranid+"&udf1="+udf1+"&udf2="+udf2+"&udf3="+udf3+"&udf4="+udf4+"&udf5="+udf5);
            
            // hard code IP ADDRESS Data to proceed 
            try {
            	//response.sendRedirect("http://10.200.4.64:8085/AlmullaExchangeService/remittance/ErrorPage.xhtml?paymentId="+paymentId+"&result="+result+"&ErrorText="+ErrorText);
            }catch (Exception e) {
    			e.printStackTrace();
    		}

        }
%>