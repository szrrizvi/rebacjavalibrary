This project is under the MIT License. Please see LICENSE.txt for more information

ReBAC Project version 0.0.4
***************************************************************
Updates: 

The project supports Hybrid-Modal logic instead of Modal logic.
Corresponding changes in ModelChecker.java

-- See ChangeLog.txt for details
***************************************************************


ReBAC Porject version 0.0.3
***************************************************************
Updates:

Changed classes representing varaints to interfaces, and moved the
concrete classes to ca.ucalgary.ispia.rebac.impl
Corresponding changes in ModelChecker.java and RebacPolicyParser

Created PolicyUtil class to provided utility methods

Minor typo fixes

-- See ChangeLog.txt for details
***************************************************************


ReBAC Project version 0.0.2
***************************************************************
Updates: 

A new package has been added; ca.ucalgary.ispia.rebac.parsers which contains
the ReBAC Policy Parser.

An XML Schema has been added to src/main/resources to help initialize ReBAC
policies through XML files.

A test environment has been added in this version along with a couple tests.

A new package (testClasses) has been added under src/test to hold concrete 
classes for testing purposes.

RelationshipIdentifier removed, now relation identifiers represent by Object class

Relationship Identifier now known as Relation Identifier (fixed typo)

-- See ChangeLog.txt for details
***************************************************************


ReBAC Project version 0.0.1
***************************************************************
The current version contains the base of the project. 
It consists of 2 packages, ca.ucalgary.ispia.rebac and ca.ucalgary.ispia.rebac.util

ca.ucalgary.ispia.rebac package contains the main bulk of the project:
- Abstract Syntax Tree classes for representing policies
- Classes for representing graphs (i.e. relational structures)
- The ModelChecker

ca.ucalgary.ispia.rebac.util contains the utility classes:
- IterablePair : Used for combining Iterable objects
- Pair : Generic relationship between 2 objects
***************************************************************

Import Project to Eclipse:
***************************************************************
To import this project to Eclipse IDE, you must install the Maven2 plugins for Eclipse.
Once you have the Maven2 plugins intalled:
File -> Import -> Maven 2 -> Maven 2 Project -> Browse -> <Find and select project root director> -> Ok -> Finish
***************************************************************


Maven Goals:
***************************************************************
To compile:
	mvn compiler:compile

To generate javadoc:
	mvn javadoc:javadoc

To create distribution:
	mvn assembly:single

To create jar package:
	mvn package
	
To run JUnit tests:
	mvn test

To clean:
	mvn clean
***************************************************************

Contributors
***************************************************************
Syed Zain Rizvi
Mona Hosseinkhani
***************************************************************

yEd
***************************************************************
yEd allows the user to visually create graphs and obtain a GraphML format file from it. We use these
GraphML files for testing purposes.

yEd is a product of yWorks, and can be obtained from:
	 http://www.yworks.com

The GraphParser, under the testClasses package in src/test section, depends on a specific type of 
GraphML file. The GraphML files should be created through yEd. The GraphParser uses yEd specific
elements, such as "nodeLabel" and "edgeLabel". "nodeLabel" is used to give custom names to nodes,
and "edgeLabel" is used for labelling edges to be used as relation identifiers.

The GraphParser is compatible with yEd version 3.9.2. The GraphParser is not guaranteed to work
with any other version of yEd.

The GraphParser is not guaranteed to work if the GraphML file is edited manually.

When would you need yEd?
	The only reason you would need to use yEd is if you are adding or manipulating test cases.
	If you are simply using this as a library or only adding features to this project (without 
	adding/changing any tests), you would not need to use yEd.
***************************************************************
