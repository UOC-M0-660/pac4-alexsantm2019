# PARTE TEORICA

### Arquitecturas de UI: MVP, MVVM y MVI

#### MVVM

##### ¿En qué consiste esta arquitectura?
El patrón Model-View-ViewModel (MVVM) nos ayuda a separar la lógica de negocios de la interfaz de usuario, facilitando las pruebas, mantenimiento y la escalabilidad de los proyectos. Existen 3 componentes que ayudan a lograrlo.
•	Modelo.-   El Modelo, más conocido como DataModel, se encarga de exponer datos relevantes a su ViewModels de una manera fácil de consumir. También debe recibir cualquier evento del ViewModel que necesita para crear, leer, actualizar o eliminar cualquier dato necesario del backend.
•	View-Model.-  El ViewModel recupera la información necesaria del modelo, aplica el operaciones necesarias y expone cualquier dato relevante para las Vistas. La plataforma Android es responsable de administrar los eventos del ciclo de vida de las clases que manejan la interfaz de usuario, como actividades y fragmentos. El sistema operativo puede destruir o volver a crear sus actividades en cualquier momento en respuesta a determinadas acciones o eventos del usuario
•	Vista.-  La Vista es con lo que la mayoría de nosotros ya estamos familiarizados, y es el único componente que el usuario final realmente interactúa. La Vista es responsable de mostrar la interfaz, y generalmente se representa en Android como Actividades o Fragmentos. Su papel principal en el patrón MVVM es observar uno o más ViewModels para obtener la información que necesita y actualice la interfaz de usuario


##### ¿Cuáles son sus ventajas?
La principal diferencia entre MVVM y el resto de patrones es que hay un fuerte énfasis en que ViewModel no debe contener ninguna referencia a Vistas. El ViewModel solo proporciona información y no le interesa lo que la consume. Esto lo hace fácil de crear una relación de uno a varios en la que sus Vistas pueden solicitar información desde cualquier ViewModel que necesiten. 

También es de destacar con respecto al patrón de arquitectura MVVM que ViewModel también responsable de exponer eventos que las Vistas pueden observar. Esos eventos pueden ser tan simple como un nuevo usuario en su base de datos o incluso una actualización de una lista completa de una película catalogar.


##### ¿Qué inconvenientes tiene?
Un problema que tienen en común los patrones de la arquitectura MVC es que los controladores a veces son muy difíciles de probar debido a su cercanía con la capa Vista. Manejando toda la manipulación de datos a ViewModels, unit las pruebas se vuelven muy fáciles ya que no tienen ninguna referencia a las Vistas.

Otro problema presente en algunas arquitecturas, MVC en particular, es que la lógica de negocio es bastante difícil de probar debido a la falta de separación de la lógica de View. Destinando toda la manipulación de datos al ViewModel, y manteniéndolo libre de cualquier Vista código, la lógica de negocio se convierte en unidad de prueba, ya que se puede ejecutar sin necesidad de el tiempo de ejecución de Android.

#### MVP

##### ¿En qué consiste esta arquitectura?
El Modelo Vista Presentador es uno de los patrones de arquitectura de desarrollo más comunes y usados en el desarrollo nativo de Android. La necesidad de utilizar este patrón surge debido a lo complicado que puede llegar a ser el mantenimiento y escalamiento de un proyecto que va creciendo a lo largo del tiempo y líneas de código. El uso de esta arquitectura lleva a que cualquier persona pueda fácilmente mejorar, actualizar, modificar o arreglar cualquier parte de la aplicación. Si bien aunque el patrón MVP establece una serie de lineamientos, debemos tener en cuenta que cada proyecto puede llegar a implementarlo con algunas variaciones, por lo que no es una solución absoluta.

##### ¿Cuáles son sus ventajas?
Vista desacoplada del resto de componentes
Lógica de presentación testeable de forma unitaria
Vistas y presenters reusables

##### ¿Qué inconvenientes tiene?
Acoplamiento bidireccional. Esto es, el presenter conoce a la vista y la vista al presenter. Además el presenter tiene dependencias con los servicios.
Al existir este acoplamiento, para testear el presenter tenemos que mockear los servicios y la vista.
Un presenter tiene dependencias con N servicios, lo que nos complica el testing del mismo ya que cada servicio que añadamos implica más mocking en los tests.
Existe tendencia a almacenar estados intermedios en el presenter (por ejemplo, el estado de un formulario), lo que nos lleva a dificultades a la hora de testear este presenter ya que hemos de configurar estos estados para cubrir todos los posibles casos.

#### MVI

##### ¿En qué consiste esta arquitectura?
Las siglas MVI se refieren a Model-View-Intent y es una de las nuevas arquitecturas reactivas que han salido recientemente para Android. Los roles de cada componente MVI son los siguientes:
Model: Model es un termino que puede variar en distintas arquitecturas, en MVI es el que representa un estado que puede ir cambiando. Por ejemplo digamos que tenemos 2 estados “IsLoading” y “DataLoaded”, si el usuario entra a la aplicación y tenemos que recoger datos, colocaremos el Model en el estado “IsLoading” y una vez tengamos los datos el estado pasará a “DataLoaded”.
View: View se encarga de observar los estados y renderizar en la vista. Por ejemplo, en el caso de observar el estado “IsLoading” renderizaremos un ProgressBar u otro componente visual, y cuando observe “DataLoaded” renderizaremos en pantalla los datos.
Intent: Intent se define como la intención (No confundir con Intent de Android) de un usuario al ejecutar cierta acción. Si el usuario hacer cierta acción, este enviará un evento y actualizará el estado de Model.

##### ¿Cuáles son sus ventajas?
“Single Source of Truth” (Única fuente de la verdad): Se refiere a que manteniendo estados de un componente en diferentes niveles puede causarnos conflicos, el cual teniendo una única fuente lo reduce todo a un “canal” y evita conflictos, con el cual una acción de un usuario no puede producir cambios indeseados ya que se rige por el “Estado de la aplicación”

Cazar errores: Gracias al apartado anterior, también nos facilita cazar los errores (bugs) que aparecen en nuestra aplicación, ya que sabemos que cierto error aparece en un estado de la aplicación, con el cual solo tendríamos que ver en que estado ha causado dicho error e investigar.

Los componentes permanecen separados al igual que el resto de arquitecturas, pero en MVI aún más. Por ejemplo la responsabilidad de la vista es solo renderizar los datos en la vista, no tiene ninguna responsabilidad más.

##### ¿Qué inconvenientes tiene?
Se acumula bastante código ya que debemos mantener un estado por cada acción del usuario.

Por cada acción del usuario creamos objetos. La creación de los objetos cuesta mucha memoria.

---

### Testing

#### ¿Qué tipo de tests se deberían incluir en cada parte de la pirámide de test? Pon ejemplos de librerías de testing para cada una de las partes. 
La idea de la pirámide es que sirva como referencia al momento de establecer la estrategia de pruebas del sistema en cuanto a performance. Esto será más o menos aplicable dependiendo de la arquitectura (es más que nada aplicable cuando hay microservicios, o al menos una capa de servicios SOAP o REST).

Niveles de la Pirámide
Paso a explicar un poco la idea detrás de cada uno de los niveles:
* Services Unit Tests: La idea es probar individualmente cada uno de los servicios de forma aislada, simulando cierta carga sobre el mismo. Preparar una prueba es sumamente simple, ya que se trata de un solo request http.
Por lo general, los tipos de dependencias asociados con las pruebas determinan qué herramienta usar:
* Si tienes dependencias en el framework de Android, en especial, aquellas que crean interacciones complejas, se recomienda incluir dependencias del framework con Robolectric.
* Si las pruebas tienen dependencias mínimas del framework de Android, o si dependen solo de sus propios objetos, es correcto incluir dependencias ficticias con un framework ficticio, como Mockito o Junit4
* Integration Tests: En estas pruebas vamos a querer probar varios servicios a la vez para ver cómo afectan la performance unos a otros. Para esto tomaremos las mismas pruebas unitarias y las combinaremos en un único escenario concurrente, así que si ya tenemos las otras preparadas, armar estas pruebas no debería ser tan complejo.


#### ¿Por qué los desarrolladores deben centrarse sobre todo en los Unido Tests?
Como desarrollador estos son algunos motivos más por los que no debes descartar la realización de estas pruebas:
•	Las pruebas unitarias son un gran avance porque te permiten realizar un buen número de pruebas en muy poco tiempo, de esta forma estas reduciendo costes.
•	Estas pruebas son capaces de demostrar que la lógica del código está bien y que va a responder en todos los casos.
•	Las pruebas aumentan la legibilidad del código y ayudan al desarrollador a entenderlo mejor, gracias a ello es mucho más fácil hacer cambios más rápido.
•	También sirven para documentar el proyecto en un tiempo más reducido.
•	Gracias a que las pruebas dividen el código en fragmentos pequeños, el desarrollador puede probar partes distintas del proyecto sin esperar a que otras estén completadas.
•	Con estas pruebas el desarrollador puede re factorizar el código más adelante, así tiene la seguridad de que el módulo sigue funcionando correctamente.
•	La mejora en la calidad final del código es notable porque al hacer pruebas constantes, el código final tendrá mucha calidad.


---

### Inyección de dependencias

#### Explica en qué consiste y por qué nos ayuda a mejorar nuestro código.
La inyección de dependencias (DI) es una técnica muy utilizada en programación y adecuada para el desarrollo de Android. Si sigues los principios de la DI, sentarás las bases para una buena arquitectura de apps.
La inyección de dependencias es un patrón de diseño de software que facilita la inversión de dependencias y se encarga de proveer dependencias en lugar de ser la propia clase quien cree el objeto. Cuando hablamos de depender de abstracciones estamos haciendo referencia a depender de supertipos que comúnmente se consigue mediante una clase abstracta o una interfaz de las cuales no podemos crear una instancia por lo que tenemos que ser capaces de crear la implementación concreta y pasarsela al objeto que la necesite mediante un constructor o un setter.

Implementar la inyección de dependencias te proporciona las siguientes ventajas:
•	Reutilización de código
•	Facilidad de refactorización
•	Facilidad de prueba


#### Explica cómo se hace para aplicar inyección de dependencias de forma manual a un proyecto (sin utilizar librerías externas).

Conceptos básicos de la inyección de dependencia manual
Considera un flujo como un grupo de pantallas de tu app que corresponden a una función. El acceso, el registro y la confirmación de compras son ejemplos de flujos. Cuando abarcas un flujo de acceso para una app para Android normal, el LoginActivity depende de LoginViewModel, que a su vez depende de UserRepository. Luego, UserRepository depende de UserLocalDataSource y UserRemoteDataSource, que a su vez depende de un servicio Retrofit

La arquitectura de apps recomendada de Android te incentiva a dividir el código en clases para beneficiarte de la separación de preocupaciones, un principio en el que cada jerarquía tiene una responsabilidad única definida. Esto genera más clases pequeñas que deben estar conectadas entre sí para satisfacer las dependencias de cada una.

