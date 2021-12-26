import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class ArithmeticAction {
    HttpServletRequest req;
    HttpServletResponse resp;
    Object obj = null;
    long value1 = 0L;
    long value2 = 0L;
    long result;
    Action action;
    String jsonBody = null;

    public ArithmeticAction(HttpServletRequest req, HttpServletResponse resp, Action action) {
        this.req = req;
        this.resp = resp;
        this.action = action;
    }

    public void doAction() throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        jsonBody = builder.toString();
        System.out.println("jsonBody = " + jsonBody);
//        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
//                Collectors.joining("\n"));
//
//        JSONObject jObj = new JSONObject(jsonBody);

        try {
            obj = new JSONParser().parse(jsonBody);
        } catch (ParseException e) {
//            e.printStackTrace();
            System.out.println("ERROR parcing");
        }

        JSONObject joReq = (JSONObject) obj;

        try {
            value1 = Long.parseLong(String.valueOf(joReq.get("value1")));
            System.out.println("value1 = " + value1);
        } catch (NumberFormatException e) {
            jsonOutput("ERROR. The first value must be a digit");
        }
        if (action == Action.DIVISION) {
            try {
                value2 = Long.parseLong(String.valueOf(joReq.get("value2")));
                if (value2 == 0) {
                    jsonOutput("ERROR. Division by 0 is prohibited");
                }
                System.out.println("value2 = " + value2);
            } catch (NumberFormatException e) {
                jsonOutput("ERROR. The second value must be a digit");
            }
        } else {
            try {
                value2 = Long.parseLong(String.valueOf(joReq.get("value2")));
                System.out.println("value2 = " + value2);
            } catch (NumberFormatException e) {
                jsonOutput("ERROR. The second value must be a digit");
            }
        }

        result = choiceOfAction(value1,value2,action);
        System.out.println("result = " + result);

        jsonOutput(String.valueOf(result));
    }

    private void jsonOutput(String output) throws IOException {
        JSONObject joResp = new JSONObject();
        joResp.put("result", output);
        System.out.println("joResp = " + joResp);
        System.out.println("------------------------------------------");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(joResp);
        out.close();
    }

    private long choiceOfAction(long firstValue, long secondValue, Action action) {
        long res = 0;
        switch (action) {
            case SUMMA -> res = firstValue + secondValue;
            case SUBTRACTION -> res = firstValue - secondValue;
            case MULTIPLICATION -> res = firstValue * secondValue;
            case DIVISION -> res = firstValue / secondValue;
        }
        return res;
    }
}


