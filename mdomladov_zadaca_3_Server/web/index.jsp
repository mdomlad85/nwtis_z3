<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="background-color: #f9f7f7;">
        <table cellpadding="10" style="margin-top: 25px">
            <form method="POST" action="${pageContext.servletContext.contextPath}/DohvatiGeolokaciju">
                <tr>
                    <td>Naziv i adresa: </td>
                    <td>
                        <input required type="text" id="naziv" name="naziv" value="${pageContext.servletContext.getAttribute("naziv")}" />
                    </td>
                    <td>
                        <input required type="text" id="adresa" name="adresa" value="${pageContext.servletContext.getAttribute("adresa")}" />
                    </td>
                    <td>
                        <button type="submit" id="geolokacija">Geo lokacija</button>
                    </td>
                </tr>
            </form>  

            <form method="POST" action="${pageContext.servletContext.contextPath}/SpremiLokaciju">
                <tr>
                    <td>Geo lokacija: </td>
                    <td colspan="2">
                        <input readonly type="text" style="width: 99%;" id="lokacija" name="lokacija" value="${pageContext.servletContext.getAttribute("lokacija")}" />
                    </td>
                    <td>
                        <button type="submit" id="spremi">Spremi</button>
                    </td>
                </tr>
            </form>

            <form method="POST" action="${pageContext.servletContext.contextPath}/DohvatiMeteo">
                <tr>
                    <td colspan="3">                 
                        <span style="color: red">${pageContext.servletContext.getAttribute("neuspjesnaPoruka")}</span>
                        <span style="color: green">${pageContext.servletContext.getAttribute("uspjesnaPoruka")}</span>
                        ${pageContext.servletContext.getAttribute("meteo")}
                    </td>
                    <td>
                        <button type="submit" id="meteoGet">Meteo podaci</button>
                    </td>
                </tr>
            </form>
        </table>
        <br />
    </body>
</html>
