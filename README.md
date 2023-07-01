# Chat Alta Disponibilidad 
>Trabajo práctico grupal para la asignatura Análisis y Diseño de Sistemas II de Ing. en Informática
---
<h3> Descripcion </h3>

Este sistema permitirá a los usuarios, intercambiar entre ellos, pequeños mensajes de
texto.

Deberá brindar alta disponibilidad al sistema.

Algunas características para tener en cuenta:
1. Una vez iniciada una sesión entre dos usuarios, los mismos pueden intercambiar
toda la cantidad de mensajes que quieran hasta que uno de los dos participantes
cierre la sesión.
2. Para iniciar un diálogo, el receptor debe estar en modo escucha. Es decir, a la
espera de que otro usuario quiera establecer el diálogo con el.
3. Solo se permite una única sesión en simultáneo. Un usuario no puede tener más
de un diálogo a la vez.
4. Para comenzar una sesión, el usuario iniciador debe ingresar la dirección IP y
puerto que corresponde al usuario con el que quiere establecer el diálogo.
5. El sistema respetará el estilo de arquitectura Cliente-Servidor
6. Al cerrar el servidor Principal deberá seguir operando con el servidor secundario y ser capaz de sumar un nuevo servidor
---
Técnicas de disponibilidad utilizadas:
---
- Monitor
- Heartbeat
- Redundancia pasiva
- Reintento
- Resincronizacion de estado 
---
⭐️ From 
- [lunalauti](https://github.com/lunalauti)
- [camicacace](https://github.com/camicacace)
