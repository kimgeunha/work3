package com.work.myapp;

import java.util.HashMap;
import java.util.Map;

public class locationdata {
        public String id;
        public int localnumber;


        public void location(){
            // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
        }

        public void location(String id,int localnumber) {
            this.id = id;
            this.localnumber = localnumber;

        }

        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("id", id);
            result.put("id", id);

            return result;
        }
}


