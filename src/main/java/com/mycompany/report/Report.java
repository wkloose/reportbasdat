package com.mycompany.report;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Report {
    private static String destFileName = "CLASSROOM.pdf";

    public static void main(String[] args) throws FileNotFoundException, JRException {
        System.out.println("generating jasper report...");

        JasperReport jasperReport = getJasperReport();

        Map<String, Object> parameters = getParameters();


        String connectionUrl = 
            "jdbc:sqlserver://localhost:58210;" +
            "database=University; user=hisyam; password=hisyam;" +
            //"integratedSecurity=false;" +
//            "encrypt=true;" +
            "trustServerCertificate=true;" +
            "loginTimeout=30;";

        try {
            Connection con = DriverManager.getConnection(connectionUrl);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
            JasperViewer.viewReport(jasperPrint, false);

            JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static JasperReport getJasperReport() throws FileNotFoundException, JRException {
        File template = Paths.get("resources/Waves_Landscape.jrxml").toFile();
        return JasperCompileManager.compileReport(template.getAbsolutePath());
    }

    private static Map<String, Object> getParameters() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Putra");
        return parameters;
    }
    }