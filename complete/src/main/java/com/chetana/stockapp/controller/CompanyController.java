package com.chetana.stockapp.controller;

import com.chetana.stockapp.common.Constants;
import com.chetana.stockapp.database.CompanyDao;
import com.chetana.stockapp.model.CompanyAddResult;
import com.chetana.stockapp.model.CompanyBean;
import com.chetana.stockapp.model.CompanyDeleteResult;
import com.chetana.stockapp.model.CompanyListResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin(maxAge = 3600)
@RestController
public class CompanyController {
	
	CompanyDao companyDao = new CompanyDao();
	
    @RequestMapping("/addCompany")
    public CompanyAddResult addCompany(@RequestParam(value="symbol", defaultValue="") String companySymbol){
        
    	CompanyAddResult result = new CompanyAddResult();
    	
    	if (companySymbol.trim().equals("")) {
    		result.setMessage(Constants.FAILURE);
    		return result;
    	}
    	
    	// Validate the Company Symbol
    	RestTemplate restTemplate = new RestTemplate();
    	String jsonString = restTemplate.getForObject(String.format(Constants.COMPANY_NAME_LOOKUP_URL, companySymbol), String.class);
    	Gson gson = new Gson();
    	JsonElement element = gson.fromJson (jsonString, JsonElement.class);
    	JsonObject jsonObj = element.getAsJsonObject();
    	String companyName = null;
    	
    	boolean valid = false;
    	JsonArray queryResult = jsonObj.getAsJsonObject("ResultSet")
 			   .getAsJsonArray("Result");
    	
    	if (queryResult != null && queryResult.size() > 0) {
    		JsonObject first = queryResult.get(0).getAsJsonObject();
    		if (first.get("symbol").getAsString().equals(companySymbol)) {
    			valid = true;
    			companyName = first.get("name").getAsString();
    		}
    	}
    	
    	if (!valid) {
    		result.setMessage(Constants.FAILURE);
    		result.setReason(companySymbol+" is not a valid symbol");
    		
    		return result;
    	}
    	
    	// Add company
    	try {
			boolean status = companyDao.addCompany(companySymbol, companyName);
			if (status) {
				result.setMessage(Constants.SUCCESS);
				result.setReason(companyName +"("+companySymbol +") " +" has been added and it's stock prices be monitored every 10 minutes.");
			}
			else
				result.setMessage(Constants.FAILURE);
			
		} catch (SQLException e) {
			String message = e.getMessage();
			result.setMessage(Constants.FAILURE);
			
			if(message.startsWith("Duplicate entry"))
				result.setReason(companySymbol+" already exists in the database");
			else
				result.setReason("Unknown database error");
		}
    	
    	return result;
    }

	@RequestMapping("/deleteCompany")
	public CompanyDeleteResult deleteCompany(@RequestParam(value="symbol", defaultValue="") String companySymbol) {

		CompanyDeleteResult result = new CompanyDeleteResult();
        if (!companySymbol.trim().equals("")) {
            // Delete company
            try {
                boolean status = companyDao.deleteCompany(companySymbol);
				if(status) {
					result.setMessage(Constants.SUCCESS);
					result.setReason("Deleted Successfully");
				}
				else {
					result.setMessage(Constants.FAILURE);
					result.setReason("Company symbol could not be found");
				}
            } catch (SQLException e) {
				System.out.println(e.getMessage());
				result.setMessage(Constants.FAILURE);
				result.setReason("Database error");
			}
			return result;
        }
		result.setMessage(Constants.FAILURE);
		result.setReason("Company symbol empty");
		return result;
    }

	@RequestMapping("/listCompanies")
	public CompanyListResult listCompanies() {
		CompanyListResult result = new CompanyListResult();
		try {
			ArrayList<CompanyBean> companyList = companyDao.listCompanies();
			if(companyList.size()>0) {
				result.setCompanyList(companyList);
				result.setMessage(Constants.SUCCESS);
                result.setReason("Companies found from DB");
				result.setCount(companyList.size());
			}
			else {
				result.setCompanyList(null);
				result.setMessage(Constants.FAILURE);
                result.setReason("No Company found in the database. Add company to view list.");
				result.setCount(0);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			result.setCompanyList(null);
			result.setMessage(Constants.FAILURE);
            result.setReason("Database error");
			result.setCount(0);
		}

		return result;
	}

    //getHistory
    @RequestMapping("/getHistory")
    public CompanyListResult getHistory(@RequestParam(value="symbol", defaultValue="") String companySymbol) {
        CompanyListResult result = new CompanyListResult();
        if (!companySymbol.trim().equals("")) {
            try {
                ArrayList<CompanyBean> companyList = companyDao.getHistory(companySymbol);
                if (companyList.size() > 0) {
                    result.setCompanyList(companyList);
                    result.setMessage(Constants.SUCCESS);
                    result.setReason("Company history found.");
                    result.setCount(companyList.size());
                } else {
                    result.setCompanyList(null);
                    result.setMessage(Constants.FAILURE);
                    result.setReason("Company symbol could not be found");
                    result.setCount(0);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                result.setCompanyList(null);
                result.setMessage(Constants.FAILURE);
                result.setReason("Database error");
                result.setCount(0);
            }
            return result;
        }
        result.setCompanyList(null);
        result.setMessage(Constants.FAILURE);
        result.setReason("Company symbol empty");
        result.setCount(0);
        return result;
    }
}

