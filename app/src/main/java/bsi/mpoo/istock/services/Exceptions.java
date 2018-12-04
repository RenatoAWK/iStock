package bsi.mpoo.istock.services;

public class Exceptions {

    public static class EmailAlreadyRegistered extends Exception{

        private ExceptionsEnum error;

        private int idError;

        public EmailAlreadyRegistered(){
            this.error = ExceptionsEnum.EMAIL_ALREADY_REGISTERED;
            this.idError = this.error.getValue();
        }

        public String getStringError(){
            return this.error.toString();
        }

        public int getIdError() {
            return this.idError;
        }

    }

    public static class ClientAlreadyRegistered extends Exception{

        private ExceptionsEnum error;

        private int idError;

        public ClientAlreadyRegistered(){
            this.error = ExceptionsEnum.CLIENT_ALREADY_REGISTERED;
            this.idError = this.error.getValue();
        }

        public String getStringError(){
            return this.error.toString();
        }

        public int getIdError() {
            return this.idError;
        }
    }
}
