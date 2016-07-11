package paqueteServlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreacionDB {

    public CreacionDB () {		
	}
    protected Connection creaDB (String ruta) {
	Connection conexion=null;
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {            
            // 1. Creaci�n de la base de datos
                // Carga del driver
                Class.forName(driver).newInstance();
                // La base de datos 'tiger' se crea (si no existe)dentro del directorio 'db'
                String cadenaConexion ="jdbc:derby:"+ruta+"\\db\\tiger;create=true";
                // Realiza la conexi�n
                conexion = DriverManager.getConnection(cadenaConexion);
	        System.out.println("1.Base de datos cargada");
            
            // 2. Creaci�n de la tabla Emp
                Statement sentencia = conexion.createStatement();                              
                if (!existeTablaEmp(sentencia)) {
                    creaTablaEmp(sentencia);            
                    // Carga inicial de la tabla Emp
                    insertaEnTablaEmp(sentencia);
                    System.out.println("2.Tabla Emp creada y rellena");
                }
                
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }
        
        return conexion;
        
    }
        
    private Boolean existeTablaEmp (Statement sentencia) throws SQLException {
        try {
            sentencia.executeQuery("SELECT * FROM Emp");
            return true;
        }
        catch (SQLException e) {
            System.out.println("La tabla Emp no existe");
            return false;
        }
    }    
        
    private void creaTablaEmp(Statement sentencia) throws SQLException {
        String consultaSQL;
            
        consultaSQL = "CREATE TABLE Emp"
                    + " (empno INT PRIMARY KEY,"
                    + " ename VARCHAR(30),"
                    + " job VARCHAR(30),"
                    + " hiredate DATE,"
                    + " sal DECIMAL,"
                    + " comm DECIMAL)";

        sentencia.executeUpdate(consultaSQL);        
    }
                    
    private void insertaEnTablaEmp(Statement sentencia) throws SQLException {
        String consultaSQL;
        consultaSQL = "INSERT INTO Emp VALUES (7839, 'KING', 'PRESIDENT', '1981-11-17', 5000, 0),"
                                           + "(7698, 'BLAKE', 'MANAGER', '1981-05-01', 2850, 0),"
                                           + "(7499, 'ALLEN', 'SALESMAN', '2000-09-01', 1500, 100)";
            
        sentencia.executeUpdate(consultaSQL);
    }
}

