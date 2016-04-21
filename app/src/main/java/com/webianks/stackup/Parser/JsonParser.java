package com.webianks.stackup.Parser;

import android.util.Log;
import com.webianks.stackup.Data.SetterGetterClass;
import com.webianks.stackup.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

   public static void  parseJson(JSONObject response){

       if(response==null || response.length()==0){
           return;
       }


       try {


           JSONArray jsonArray=response.getJSONArray("items");

           int quota_max = response.getInt("quota_max");
           int quota_remaining = response.getInt("quota_remaining");

           int api_quota = (quota_remaining*100)/quota_max;

           List<SetterGetterClass> list=new ArrayList<SetterGetterClass>();
           SetterGetterClass setterGetter=null;

           for(int i=0;i<jsonArray.length();i++){


               JSONArray tagsArray=jsonArray.getJSONObject(i).getJSONArray("tags");
               String[] tags=new String[tagsArray.length()];

               for(int j=0;j<tagsArray.length();j++){
                   tags[j]=tagsArray.getString(j);
               }

               JSONObject owner=jsonArray.getJSONObject(i).getJSONObject("owner");



               String profile = owner.getString("profile_image");
               String name = owner.getString("display_name");

               String title = jsonArray.getJSONObject(i).getString("title");
               String link = jsonArray.getJSONObject(i).getString("link");
               long last_activity_date = jsonArray.getJSONObject(i).getLong("last_activity_date");
               long creation_date = jsonArray.getJSONObject(i).getLong("creation_date");

               int views = jsonArray.getJSONObject(i).getInt("view_count");
               int answers = jsonArray.getJSONObject(i).getInt("answer_count");
               int score = jsonArray.getJSONObject(i).getInt("score");

               setterGetter=new SetterGetterClass();



               setterGetter.setTitle(title);
               setterGetter.setLink(link);
               setterGetter.setName(name);
               setterGetter.setProfile(profile);
               setterGetter.setTags(tags);
               setterGetter.setViews(views);
               setterGetter.setAnswers(answers);
               setterGetter.setScore(score);
               setterGetter.setDate(last_activity_date);
               setterGetter.setCreationDate(creation_date);

               list.add(setterGetter);

               setterGetter=null;

           }

           MainActivity.jsonCallback(list,api_quota);


       } catch (JSONException e) {
           Log.d("Webi",e.getMessage().toString());
       }
   }

}
