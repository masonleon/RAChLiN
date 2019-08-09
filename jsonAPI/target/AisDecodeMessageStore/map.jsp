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
            margin-left: auto;
            margin-right: auto;
            height: 720px;
        }
    </style>
</head>
<body>
<h2>Datapoints Received</h2>
<%--TODO Display the number of points received.--%>
<p>To filter results to only datapoints between a certain period of time, enter start and stop times
    in a &quot;YYYY-MM-DD HH:MM:SS&quot; format (All times should be in UTC).</p>
<p>There is a current limit of 5000 points returned. This is a performance issue resulting from the
    design of our logic. It's likely that part of the code should be moved server side.</p>
<p>Displaying results between times:</p>
<p><s:property value="timeSpan.getStartTime"/></p>
<p><s:property value="timeSpan.getEndTime"/></p>
<s:form action="time">
    <s:textfield name="timeSpan.startTime" label="Start time"/>
    <s:textfield name="timeSpan.endTime" label="End time"/>
    <s:submit/>
</s:form>
<div id="mapid"></div>
<script>
    // TODO remove
    console.log("Executing script");

    var COLORS = ["yellow", "orange", "red", "green", "blue", "black", "white"];

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

    var colorCounter = 0;
    var mapStructure = new Map();
    for (var x in raw_data) {
        if (mapStructure.has(raw_data[x]["vessel_signature_id"])) {
            var iconColor = mapStructure.get(raw_data[x]["vessel_signature_id"]);
        } else {
            var iconColor = COLORS[colorCounter];
            mapStructure.set(raw_data[x]["vessel_signature_id"], COLORS[colorCounter]);
            colorCounter = (colorCounter + 1) % COLORS.length;
        }

        console.log(x);
        var coord = raw_data[x]["coord"];
        lat_lon = coord.replace('(', '').replace(')', '').split(',');
        latitutde = Number(lat_lon[0]);
        longitude = Number(lat_lon[1]);
        L.circle([latitutde, longitude], 15,
                 {color: iconColor, fillColor: iconColor, fillOpacity: 1.0})
            .addTo(mymap)
            .bindPopup("name: " + raw_data[x]["name"] + "\n" +
                       "call_sign: " + raw_data[x]["call_sign"] + "\n" +
                       "time_received: " + raw_data[x]["time_received"] + "\n" +
                       "coord: " + raw_data[x]["coord"] + "\n" +
                       "accuracy: " + raw_data[x]["accuracy"] + "\n" +
                       "length_overall: " + raw_data[x]["length_overall"] + "\n" +
                       "beam_width: " + raw_data[x]["beam_width"] + "\n" +
                       "speed_over_ground: " + raw_data[x]["speed_over_ground"] + "\n" +
                       "course_over_ground: " + raw_data[x]["course_over_ground"] + "\n" +
                       "heading: " + raw_data[x]["heading"] + "\n" +
                       "rate_of_turn: " + raw_data[x]["rate_of_turn"] + "\n" +
                       "navigation_status: " + raw_data[x]["navigation_status"] + "\n" +
                       "maneuver_indicator: " + raw_data[x]["maneuver_indicator"] + "\n" +
                       "ship_classification: " + raw_data[x]["ship_classification"] + "\n" +
                       "ais_vessel_code: " + raw_data[x]["ais_vessel_code"] + "\n" +
                       "vessel_group: " + raw_data[x]["vessel_group"] + "\n" +
                       "note: " + raw_data[x]["note"]);
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
