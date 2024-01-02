package com.deporte.plantilla.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ReporteController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @RequestMapping("/reportes/{reportName}")
    public void demoReport1(@PathVariable("reportName") final String reportName, @RequestParam(required = false) Map<String, Object> parameters, HttpServletResponse response,
                            HttpServletRequest request) throws Exception {

        parameters = parameters == null ? new HashMap<>() : parameters;
        ClassPathResource resource = new ClassPathResource("reportes" + File.separator + reportName + ".jasper");
        InputStream jasperStream = resource.getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline;");
        final OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }

    @PostMapping("/reporte/equipo")
    public void verFiltro(@RequestParam(name="usuario") String usuario, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", usuario);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/equipo.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/jugador")
    public void verFiltro(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/jugador.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/partido")
    public void verFiltros(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/partido.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/statsPartido")
    public void verStatsPartidoxEquipo(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/statsPartido.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/promJugador")
    public void verPromediosJugadorxEquipo(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/promJugador.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Promedio de jugador por equipo y por temporada*/
    @PostMapping("/reporte/promJugadorTemp")
    public void verPromediosJugadorxEquipoxTemporada(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/promJugador.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/efectJugador")
    public void verEfectividadJugadorxEquipo(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/efectJugador.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/acumJugador")
    public void verAcumuladosJugadorxEquipo(@RequestParam(name="codequipo") int codequipo, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codequipo", codequipo);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/acumJugador.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/datosPartido")
    public void verDatosdelPartidoxjugador(@RequestParam(name="codpartido") int codpartido, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codpartido", codpartido);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/datosPartido.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Para Jugadores*/
    @PostMapping("/reporte/promIndividual")
    public void verPromediojugador(@RequestParam(name="codjugador") int codjugador, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codjugador", codjugador);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/promIndividual.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/efectIndividual")
    public void verEfectividadjugador(@RequestParam(name="codjugador") int codjugador, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codjugador", codjugador);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/efectIndividual.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/reporte/acumIndividual")
    public void verAcumuladojugador(@RequestParam(name="codjugador") int codjugador, HttpServletResponse response) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("codjugador", codjugador);

        response.setHeader("Content-Disposition", "inline;");
        response.setContentType("application/pdf");
        try {
            String ru = resourceLoader.getResource("classpath:reportes/acumIndividual.jasper").getURI().getPath();
            JasperPrint jasperPrint = JasperFillManager.fillReport(ru, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*CONSULTAS*/




}
