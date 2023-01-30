package com.automation.utils.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;

public class RestAssuredServices {
    private RequestSpecification request;

    public RestAssuredServices(String url){

        RestAssured.baseURI =url;
        request  = RestAssured.given();
    }

    public Response postRequest(String Headers, String parameters, String Body,String authType,String username,String password){
        addHeaders(Headers);
        addParameters(parameters);
        addBody(Body);
        addAuthorization(authType, username, password);
        return request.post();

    }

    public Response getRequest(String Headers,String parameters,String authType,String username,String password) {
        addHeaders(Headers);
        addParameters(parameters);
        addAuthorization(authType, username, password);
        return request.get();
    }
    //add headers
    public void addHeaders(String Headers){
        if(Headers!=null && !Headers.isEmpty()){
            String[] headers= Headers.split("&");
            for (String header : headers)
            {
                request.header(header.split("=")[0].trim(), header.split("=")[1].trim());
            }
        }


    }
    //add parameters
    public void addParameters(String Parameters){
        if(Parameters!=null && !Parameters.isEmpty()){
            String[] parameters= Parameters.split("&");
            for (String parameter : parameters)
            {
                request.param(parameter.split("=")[0].trim(), parameter.split("=")[1].trim());
            }
        }
    }

    //add parameters
    public void addFormParameters(String Parameters){
        if(Parameters!=null && !Parameters.isEmpty()){
            String[] parameters= Parameters.split("&");
            for (String parameter : parameters)
            {
                request.formParam(parameter.split("=")[0], parameter.split("=")[1]);
            }
        }




    }

    //add body for a post request
    public void addBody(String body){
        if(body!=null && !body.isEmpty()){
            request.body(body);
        }

    }

    //add auth
    public void addAuthorization(String authType,String userName,String password){
        AuthenticationSpecification auth = request.auth();
        try {
        if(authType.equalsIgnoreCase("basicauth") && authType != null) {
        	auth.basic(userName, password);
        } 
        }catch (Exception e) {
        	e.getMessage();
		}
        
    }
}
