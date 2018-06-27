package info.androidhive.navigationdrawer.event;



/**
 * Created by Rohit Pawar on 12-04-2018.
 */


public class LoginEvent {

        private String data;

        public LoginEvent(String data){
            this.data = data;
        }

        public String getData(){
            return data;
        }
    }

