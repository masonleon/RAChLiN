$(document).ready(function () {

      let $mapId;
      let $mymap;

      $(main);

      function main() {
        // var leafletStyle = document.createElement('link');
        // leafletStyle.style = "stylesheet";
        // leafletStyle.href = "https://unpkg.com/leafletJS@1.6.0/dist/leafletJS.css";
        // leafletStyle.integrity = "sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ==";
        // leafletStyle.crossOrigin = "";
        // document.getElementsByTagName("head")[0].appendChild(leafletStyle);

        // var leafletJS = document.createElement('script');
        // leafletJS.src = "https://unpkg.com/leaflet@1.6.0/dist/leaflet.js";
        // leafletJS.integrity = "sha512-gZwIG9x3wUXg2hdXF6+rVkLF/0Vi9U8D2Ntg4Ga5I5BZpVkVxlJWbSQtXPSiUTtC0TjtGOmxa1AJPuV0CPthew==";
        // leafletJS.crossOrigin = "";
        // document.getElementsByTagName("head")[0].appendChild(leafletJS);


        console.log("in main");
        // $mapId = $('#mapid');
        $mapId = document.getElementById("mapid");
        loadMap();
        console.log("map loaded");
      }

      function loadMap() {
        console.log("loading map");
        // let $mymap = L.map($mapId).setView([42.3417649, -70.966159], 10);
        var mymap = L.map($mapId).setView([42.3417649, -70.966159], 10);

        L.tileLayer(
            'https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?'
            + 'access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw',
            {
              attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, '
                  + '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
              maxZoom: 18,
              id: 'mapbox/streets-v11',
            }).addTo(mymap);
        // }).addTo($mymap);
      }
    }
);












