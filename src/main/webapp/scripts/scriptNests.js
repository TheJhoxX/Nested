
window.onload = init;
var invitados = [];
var formularioPublicar;
var popup = L.popup();
var map;
var marker = null;
var geocoder;


function init() {
    formularioPublicar = document.getElementById("formulario-publicar");
    formularioPublicar.addEventListener('submit', comprobarFormulario);   
    
    var amigos = document.querySelectorAll('.invitacion');
    for (var i = 0; i<amigos.length; i++){
        var amigo = amigos[i];
        amigo.addEventListener('click',agregarParticipante);   
    }
    
    var mapas = document.querySelectorAll('[id^="map:"]');
    if (mapas.length !== undefined){
        for (var i = 0; i<mapas.length; i++){
        
        /*Encapsular dentro de una función para asegurar de que las promesas
         * han finalizado antes de pasar a la siguiente iteración
         */
            (function(index) {
                var idMapa = mapas[index].id;
                var direccion = idMapa.split(':')[1];
                var latitud = parseFloat(direccion.split(',')[0]);
                var longitud = parseFloat(direccion.split(',')[1]);

                var mapInfo = L.map(idMapa).setView([latitud,longitud], 16);
                L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
                  maxZoom: 19,
                  attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
                }).addTo(mapInfo);
                L.marker([latitud, longitud]).addTo(mapInfo);

        })(i);
    }
    
    
    
    var parrafosUbicacion = document.querySelectorAll('[id^="display-name:"]');
    if (parrafosUbicacion !== undefined){
        for(var i = 0; i<parrafosUbicacion.length; i++){
            (function(index){
                var idParrafo = parrafosUbicacion[index].id;
                var direccion = idParrafo.split(':')[1];
                var latitud = parseFloat(direccion.split(',')[0]);
                var longitud = parseFloat(direccion.split(',')[1]);
                var texto = parrafosUbicacion[i];
                obtenerDireccion([latitud,longitud])
                    .then(function(direccion) {
                        texto.innerText = direccion;
                    })
                    .catch(function(error){
                        console.error('Error al obtener la dirección:', error);
                    });
        })(i);
    }
    
        
    }
        

        var botonesInscripcion = document.querySelectorAll('[id^="popupInscripcion-"]');

            for (var i = 0; i<mapas.length; i++){
                if (botonesInscripcion[i] !== undefined){
                    botonesInscripcion[i].addEventListener('submit', controlarApuntado);
                }                        
            }
        
        
    }
    
    
    
    //Mapa de la publicacion de Nests
    map = L.map('map').setView([40.4167754,-3.7037902],13);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    // Crear un objeto de geocodificación con Nominatim
    geocoder = L.Control.geocoder({
        defaultMarkGeocode: false
    })
    .on('markgeocode', function(e) {

               
        establecerContenidoPopup(e.geocode.center);
        
    }).addTo(map);

    map.on('click',onMapClick);
}

function onMapClick(e) {
    establecerContenidoPopup(e.latlng);    
}

function establecerContenidoPopup(latlng) {
    obtenerDireccion([latlng.lat,latlng.lng])
    .then(function(direccion) {
      
      popup
        .setLatLng(latlng)
        .setContent(direccion)
        .openOn(map);
      
      if (marker !== null) {
        marker.remove();
      }
      
      marker = L.marker(latlng).addTo(map);
      console.log('MARKER: ' + marker.getLatLng().toString());
    })
    .catch(function(error) {
      console.error('Error al obtener la dirección:', error);
    });
}

function obtenerDireccion(latlng) {
  var latitud = latlng[0];
  var longitud = latlng[1];
  var url = 'https://nominatim.openstreetmap.org/reverse?format=json&lat=' + latitud + '&lon=' + longitud;

  return new Promise(function(resolve, reject) {
    fetch(url)
      .then(function(response) {
        return response.json();
      })
      .then(function(data) {
        var direccion = data.display_name;
        resolve(direccion); // Resolvemos la promesa con la dirección obtenida
      })
      .catch(function(error) {
        reject(error); // Rechazamos la promesa en caso de error
      });
  });
}

function controlarApuntado(e){
    var idBoton = e.target.id;
    var cantidades = idBoton.split('-')[1];
    var participantes = cantidades.split('/')[0];
    var limite = cantidades.split('/')[1];

    if (participantes === limite){
        e.preventDefault();
        alert('Este Nest ya ha llegado al límite de participantes');
        return false;
    }
    return true;
}


function comprobarFormulario(e) {
    // Obtener el campo de fecha y hora del formulario
    var fechaInicio = document.getElementById("fechaInicio");
    var fechaFin = document.getElementById("fechaFin");

    // Obtener la fecha y hora actual
    var now = new Date();

    // Obtener la fecha y hora del campo del formulario
    var inputDateInicio = new Date(fechaInicio.value);
    var inputDateFin = new Date(fechaFin.value);


    // Comprobar si la fecha y hora del campo del formulario es anterior a la fecha y hora actual
    if ((inputDateInicio < now) || (inputDateFin < now) || (inputDateFin < inputDateInicio)) {
        alert("La fecha y hora de comienzo y finalización no pueden ser anteriores al día de hoy"+
                " y la fecha de finalización debe ser posterior a la de comienzo");
        e.preventDefault(); // Evita que se envíe el formulario
        return false;
    }

    e.preventDefault();
    var nombre = document.querySelector('[name="nombre"]');
    var descripcion = document.querySelector('[name="descripcion"]');

    if (marker === null){
        alert("¡No puedes publicar un Nest sin añadir una ubicación!");
        e.preventDefault();
        return false;
    }


    var limitePersonas = document.querySelector('[name="limitePersonas"]');
    var publico = document.querySelector('[name="publico"]');
    var archivo = document.querySelector('[name="imagenFondo"]');

    var imagen;
    if (archivo.files.length > 0) {
        imagen = archivo.files[0];
    }
    else {
        imagen = null;
    }

    var ubicacion;

    var formData = new FormData();
    formData.append("invitados", JSON.stringify(invitados));
    formData.append("nombre",nombre.value);
    formData.append("descripcion",descripcion.value);
    console.log('UBICACION QUE SE GUARDA:' + marker.getLatLng().toString());
    formData.append("ubicacion",formarStringCoordenadas(marker.getLatLng().toString()));
    formData.append("limitePersonas",limitePersonas.value);

    if (publico.checked){
        formData.append("publico","true");
    }
    else {
        formData.append("publico","false");
    }

    formData.append("fechaInicio", fechaInicio.value);
    formData.append("fechaFin", fechaFin.value);
    formData.append("imagenFondo", imagen);






    fetch("servletNests", {
     method: "POST",
     body: formData
    })
     .then(function (response) {
       if (response.ok) {
            window.location.href = "/ServletSeccionNest?tipo=2";
            alert("¡El Nest: " + nombre.value + " se ha publicado correctamente!");
       } else {
         throw new Error("Error en la solicitud");
       }
    })
     .catch(function (error) {
       console.error("Error en la solicitud:", error);
    });


    return true;

  
}


function agregarParticipante(event) {
    var invitado = event.target;
    var estilo = getComputedStyle(invitado);
    var color = estilo.backgroundColor;
    var arroba = invitado.querySelector('p').textContent;

    if (color !== 'rgb(137, 13, 198)') {
        invitados.push(arroba);
        invitado.style.backgroundColor = 'rgb(137, 13, 198)';
    } else {
        var posicion = invitados.indexOf(invitado);
        invitados.splice(posicion, 1);
        invitado.style.backgroundColor = 'rgb(59, 59, 59)';
    }
}

    function obtenerCoordenadas(direccion) {
      var url = 'https://nominatim.openstreetmap.org/search?format=json&q=' + encodeURIComponent(direccion);

      return fetch(url)
        .then(function(response) {
          return response.json();
        })
        .then(function(data) {
          if (data.length > 0) {
            var latitud = parseFloat(data[0].lat);
            var longitud = parseFloat(data[0].lon);
            return { latitud: latitud, longitud: longitud };
          } else {
            throw new Error('No se encontraron coordenadas para la dirección proporcionada');
          }
        })
        .catch(function(error) {
          console.error('Error al obtener las coordenadas:', error);
          throw error;
        });
    
    }
    
    function formarStringCoordenadas(coordenadas) {
        var coordenadaString = coordenadas.toString();

        // Expresión regular para extraer los números
        var regex = /-?\d+\.?\d*/g;

        // Array para almacenar los números extraídos
        var numeros = [];

        // Extraer y convertir los números
        var match;
        while ((match = regex.exec(coordenadaString))) {
          numeros.push(parseFloat(match[0]));
        }

        // Mostrar los números extraídos
        return numeros[0].toString() + ',' + numeros[1].toString();
        
    }
    






