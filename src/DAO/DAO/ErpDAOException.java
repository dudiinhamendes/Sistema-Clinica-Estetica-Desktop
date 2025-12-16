
package DAO.DAO;


public class ErpDAOException extends Exception{
 
 public ErpDAOException(){

    }

    public ErpDAOException(String arg){
        super(arg);
        System.out.println("Erro"+ arg);
    }

    public ErpDAOException(Throwable arg){
        super(arg);
        System.out.println("Erro" + arg);
    }

    public ErpDAOException(String arg, Throwable arg1){
        super(arg, arg1);
        System.out.println("Erro" + arg);
    }

}

