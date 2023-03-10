package com.automation.utils.services;

public class RestServiceRunner {
    RestClient client;

    public RestServiceRunner(
             String client,
             String url,
             String headers,
             String parameters,
             String authType,
             String user,
             String pass) throws Exception{

        if(client.equalsIgnoreCase("get")){
            this.client = new GETClient(url);
        }else if(client.equalsIgnoreCase("post")){
            this.client = new POSTClient(url);
        }else{
            throw new Exception("InvalidClientType");
        }

        addHeadersFromString(headers);
        addParametersFromString(parameters);
        addAuthFromString(authType, user, pass);
    }

    public void addParametersFromString(String param) throws Exception{
        if(param != null && !param.equals("null")){
            String[] kVList = param.split("&");

            for(int i = 0; i < kVList.length; i++){
                String[] kv = kVList[i].split("=");
                client.addParameter(kv[0], kv[1]);
            }
        }
    }

    public void addHeadersFromString(String header) throws Exception{
        if(header != null && !header.equals("null")){
            String[] kVList = header.split("&");

            for(int i = 0; i < kVList.length; i++){
                String[] kv = kVList[i].split("=");
                client.addHeader(kv[0], kv[1]);
            }
        }
    }

    public void addAuthFromString(String authType, String user, String pass) throws Exception{
    	if(authType.equalsIgnoreCase("noauth")) {
    		System.out.println("no auth");
    	} else if(authType.equalsIgnoreCase("basicauth")) {
    		client.addBasicAuth(user, pass);
    	} else if(authType.equalsIgnoreCase("digestauth")) {
    		throw new Exception("digestauth not implemented");
       	} else if(authType.equalsIgnoreCase("auth1")) {
       		throw new Exception("auth1 not implemented");
       	} else if(authType.equalsIgnoreCase("auth2")) {
       		throw new Exception("auth2 not implemented");
       	} else {
       		throw new Exception("Invalid Auth Type");
       	}
    }

    public String run() throws Exception{
        client.send();
        return client.readResponseAsString();
    }
}
