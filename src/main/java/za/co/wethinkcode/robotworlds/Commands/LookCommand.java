package za.co.wethinkcode.robotworlds.Commands;import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;public class LookCommand {
    private String robot;
    private JSONArray objects;    public void setObjects(JSONArray objects) {
        this.objects = objects;
    }    public void setRobot(String robot) {
        this.robot = robot;
    }    public String generateRequest() throws JSONException{
        JSONObject request = new JSONObject();
        request.put("robot", robot);
        request.put("command", "look");
        request.put("arguments", new JSONArray());
        return request.toString();
    }    public String generateResponse() throws JSONException{
        JSONObject response = new JSONObject();
        response.put("result", "OK");        JSONObject data = new JSONObject();
        JSONArray objectsArray = new JSONArray();        // Add obstacles object
        JSONObject obstacleObject =new JSONObject();
        obstacleObject.put("type", "OBSTACLE");
        obstacleObject.put("distance", 1);
        objectsArray.put(obstacleObject);        // Add robot objects
        for (int i = 1; i <= 3; i++){
            JSONObject robotObject = new JSONObject();
            robotObject.put("type", "ROBOT");
            robotObject.put("distance", 1);
            objectsArray.put(robotObject);
        }        data.put("objects", objects);
        response.put("data", data);        JSONObject state = new JSONObject();
        //st the state of the robot if applicable
        response.put("state", state);        return response.toString();
    }
}