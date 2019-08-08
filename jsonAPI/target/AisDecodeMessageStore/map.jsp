<%--
  Created by IntelliJ IDEA.
  User: matthias
  Date: 2019-08-08
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>AIS Decode Message Store</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"
          integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"
            integrity="sha512-GffPMF3RvMeYyc1LWMHtK8EbPv0iNZ8/oTtHPx9/cc2ILxQ+u905qIwdpULaqDkyBKgOaB57QTMg7ztg8Jm2Og=="
            crossorigin=""></script>
    <style>
        #mapid {
            height: 1080px;
        }
    </style>
</head>
<body>
<div id="mapid"></div>
<p><s:property value="queryResult.getMessage"/></p>
<script>
    var mymap = L.map('mapid').setView([42.3417649, -70.966159], 10);

    L.tileLayer(
        'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
        {
            maxZoom: 18,
            attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, '
                         +
                         '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
                         +
                         'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
            id: 'mapbox.streets'
        }).addTo(mymap);

    var raw_data =
    <s:property value="queryResult.getMessage" escapeHtml="a default value" />

    for (var x in raw_data) {
        console.log(x);
        var coord = raw_data[x]["coord"];
        lat_lon = coord.replace('(', '').replace(')', '').split(',');
        latitutde = Number(lat_lon[0]);
        longitude = Number(lat_lon[1]);
        L.circle([latitutde, longitude], 15,
                 {color: "white", fillColor: "white", fillOpacity: 1.0})
            .addTo(mymap).bindPopup("vessel_signature_id: " + raw_data[x]["vessel_signature_id"]);
    }

    var popup = L.popup();

    function onMapClick(e) {
        popup
            .setLatLng(e.latlng)
            .setContent("You clicked the map at " + e.latlng.toString())
            .openOn(mymap);
    }

    mymap.on('click', onMapClick);
</script>

</body>
</html>
