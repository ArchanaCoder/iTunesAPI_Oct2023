package configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestEnvProperties {

    public static Map<String,String> envpropdata = new HashMap<>();
    public static Properties prop = new Properties();

    private String apiurl;

    FileInputStream propfile;

    {
        try {
            propfile = new FileInputStream(System.getProperty("user.dir")+ File.separator +
                    "src"+ File.separator + "main"+ File.separator + "resources"+ File.separator + "env.properties");
            prop.load(propfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String env = prop.getProperty("ENV");


        if(env.equals("SIT")){
            apiurl = prop.getProperty("sit.api.url");

        } else if (env.equals("UAT")) {
            apiurl = prop.getProperty("uat.api.url");
        }

    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

}
