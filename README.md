# Créer une application java avec spring boot

[Tutoriel Openclassrooms](https://openclassrooms.com/fr/courses/6900101-creez-une-application-java-avec-spring-boot/7077979-creez-votre-projet)

**Environnement**
+ Java 8
+ IntelliJ Community

## Création du projet spring boot

Peu importe la méthode utilisée, le fichier `pom.xml` sera automatiquement généré à partir de nos inputs.

Pour rappel, **un bean est une classe au sein du contexte Spring**.

### Utiliser le site web

Utiliser le site web [Spring Initializr](https://start.spring.io/).

Dans ce projet, on utilise maven, java 8, spring boot 2.5.4 et la packaging jar. Puis clique sur `Generate CTRL + Entrée` et on télécharge le zip à importer dans l'IDE.

### Spring tools for Eclipse

Il est téléchargeable sur Linux, macOS et Windows. Vous obtenez un JAR qui, une fois exécuté, va créer un répertoire qui aura un nom du style “sts-4.7.1.RELEASE”. Au sein de ce répertoire, vous pouvez lancer l’exécutable “SpringToolSuite4.exe”.

*Spring Tools 4 for Eclipse est un outil développé sur la base de l’IDE Eclipse. Les habitués de cet IDE ne seront donc pas dépaysés !*

**Mais pourquoi utiliser Spring Tool Suite et non Spring Initializr, qui ne demande aucun outil supplémentaire ?**

Pour la simple raison que STS (Spring Tool Suite) nous offre une fonctionnalité supplémentaire : le “Boot Dashboard”, qui permet de gérer plus précisément le cycle de vie de l’exécution de l’application. Et comme STS est tout simplement un Eclipse customisé, on peut directement développer notre projet au sein de l’outil.

### Spring framework avec IntelliJ

La version Community ne permet pas de créer un projet directement avec le framework spring boot. Il faut utiliser IntelliJ Ultimate (payant).

## Structure d'un projet spring

### Structure des packages

La majorité des applications ont la nécessité d'interagir avec des données externes (BDD, autre programme, système de fichiers, ...). De ces différents besoins, une architecture en couches a émergé, avec un rôle pour chaque couche:
+ **couche Controller** : gestion des interactions entre l’utilisateur de l’application et l’application ;
+ **couche Service** : implémentation des traitements métiers spécifiques à l’application ;
+ **couche Repository** : interaction avec les sources de données externes ;
+ **couche Model** : implémentation des objets métiers qui seront manipulés par les autres couches.

*D’autres packages peuvent être parfois nécessaires, par exemple un nommé “security” pour les classes dédiées à la sécurité, ou “configuration” pour des classes gérant les propriétés. Dans ce cas, cela s'ajoute à la structure existante.*

### Le fichier application.properties

Pour en savoir plus sur les propriétés de Spring, il faut se référer à la [documentation de Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html), les principales étant :
+ **spring.application.name** : nom de l'app
+ **logging.level.[package]** : permet d'indiquer le log level pour un package donné

Là où Spring Boot nous intéresse, c’est qu’il est capable de lire ces sources de propriétés (sans interaction de notre part), et de rendre les propriétés disponibles sous forme de beans au sein du contexte Spring.

Parmi les sources de propriétés, il y a :
+ les propriétés de la JVM ;
+ les variables d’environnements du système d’exploitation ;
+ les arguments passés par la ligne de commande ;
+ les fichiers .properties ou .yml (comme application.properties).

## Ecriture du code

Après avoir structuré et configuré, il reste à coder.

La méthode main sera théoriquement là où on écrirait notre code dans un programme Java simple. Mais en l'occurrence, cette dernière contient l’instruction “SpringApplication.run(HelloWorldApplication.class, args);”. Cette instruction permet de démarrer notre application, et ce n’est pas une bonne pratique d’ajouter autre chose dans la méthode main.

Oui, mais où met-on notre code, alors ?

Spring Boot fournit une interface nommée `CommandLineRunner`. En implémentant cette interface, la classe sera obligée de déclarer la méthode `public void run(String... args) throws Exception`. À partir de là, si la classe est un bean (c’est-à-dire chargée dans le contexte Spring), Spring Boot exécutera la méthode run à l’exécution du programme.
Vous pourriez :
+ soit modifier la classe HelloWorldApplication afin qu’elle implémente CommandLineRunner et la méthode run, avec comme corps de méthode un `System.out.prinln("Hello World!")` ;
+ soit créer une nouvelle classe qui implémente CommandLineRunner, la méthode run (même corps de méthode), et qui aura une annotation `@Component` (au-dessus du nom de la classe).

#### A retenir

+ Pour qu'une classe soit déclarée en tant que bean, on l'annote `@Component`.
+ Pour qu'un bean soit injecté dans un attribut, on annote l'attribut `@Autowired`.


## Unitary tests

Dans ce projet, `src/test/java/com.openclassrooms.helloworld/HelloworldApplicationTests` a une méthode `contextLoads`, annotée  `@Test` ;
elle n'a pas de contenu. En fait, son unique objectif est de vérifier que le contexte Spring se charge bien.

Sans méthode de tests (sans méthode avec @Test), notre classe de test ne peut être exécutée, même si elle est annotée @SpringBootTest. Pour parer à cela, Spring Boot génère une méthode vide annotée @Test, et qui sera donc toujours success pour JUnit (car elle est vide). 
Ainsi, lors de l’exécution de cette méthode, le contexte Spring sera chargé, et si ce dernier rencontre une erreur, alors l’exécution de la classe de test retournera une erreur.

## Déploiement

Différentes méthodes (avec résultat identique) :
+ A travers l'IDE
+ Avec le goal Maven `mvn spring-boot:run`
+ En exécutant le JAR grâce à la commande `java -jar`
```
mvn package
cd target
java -jar helloworld-0.0.1-SNAPSHOT.jar
```

A noter que dans le contexte professionnel, on est souvent amené  à livrer un JAR, et c'est ce dernier qui sera exécuté. Les 2 premières solutions sont surtout utiles pour les développeurs.