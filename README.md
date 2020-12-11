# GredStudentApp

OVERVIEW AND FEATURES:

 -Students can create an account and then login to use services
 
 -Add/delete/update subjects and corresponding marks
 
 -Rigorous validations on above operations in place
 
 -Subject grade calculated with percentage and CGPA
 
 -Reset account password
 
 -Deactivate account
 
 -Object oriented, scalable design
 

SERVICE CLASS AND OBJECT ORIENTATION:

 -Created a separate service class that provides service to all activities and carries out specific operations with methods like calculateCGPA( ), getPercentage( ), getGrade( )

 -Object oriented, scalable design
 
 -Objects transferred from one activity to another through intents in form of extras
 
 -All Models have implemented Serializable Interface.
 
 -To retrieve object on a new activity, use intent.getSerializableExtra(<label>) method


MODELS:

 -Created Models that map to the attributes of the entities in the database.
 
 -Mapped objects to relations [ORM]
 
 -Implemented Encapsulation in the java classes
 
 -Generated getters and setters for the mapped variables
 
 -toString() method of java.lang.Object class was overrided in every model to print object attributes while debugging
 
 -The objects were transferred from one activity to another
 

  


