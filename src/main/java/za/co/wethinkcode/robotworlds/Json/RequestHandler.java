package za.co.wethinkcode.robotworlds.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {
    String command;
    String name;
    String arg;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public String getCommand(){
        return command;
    }

    public void setArg(String arg){
        this.arg = arg;
    }

    public String handleRequest() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("name",name);
        jsonObject.put("command",command);
        jsonArray.put(arg);
        jsonObject.put("arguments",arg);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }


    public void setArgument(String argument) {
    }
}
