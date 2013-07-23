# Usage

## Create Some Reports

In first place you need some reports. To get them you need [Checkstyle][1] of course.

### Install Checkstyle For Command Line

- On __Debian__ systems type: `apt-get install checkstyle`
- On __Mac OS X__ with [Homebrew][2] type: `brew install checkstyle`

## Create Reports On Command Line

Choose source code you desire to create reports from. In the examples here a fictional
source is used. Also you need a [Checkstyle configuration][4]. A typical place to store
this configuration file in a [Mavne][3] projetc is `src/main/config/checkstyle-ruleset.xml`.
For this example the configuration for this project is used. For generating a report
get the source and type:

    $ cd ~/foo
    $ checkstyle -c src/main/config/checkstyle-ruleset.xml -f xml -o checkstyle-warnings.xml -r src/main/java

This will produce a file `checkstyle-warnings.xml` which may looks like this for example:

```
<?xml version="1.0" encoding="UTF-8"?>
<checkstyle version="5.6">
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/ApplicationService.java"></file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider.java">
    <error line="15" column="8" severity="warning" message="Unused import - com.vaadin.data.Validatable."
           source="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
    <error line="25" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck"/>
    <error line="26" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck"/>
    <error line="28" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
    <error line="34" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
    <error line="38" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
  </file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/package-info.java"></file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/RegistrationService.java"></file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/ServiceExcpetion.java"></file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/UserService.java">
    <error line="34" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
    <error line="38" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
  </file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/registration/RegisrationUI.java">
    <error line="32" column="8" severity="warning" message="Unused import - org.lafayette.server.domain.Finders."
           source="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/Registry.java">
    <error line="74" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck"/>
    <error line="218" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
    <error line="222" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
  </file>
  <file name="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/resources/BaseResource.java">
    <error line="159" column="5" severity="error" message="Missing a Javadoc comment."
           source="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck"/>
  </file>
</checkstyle>
```

### Generate Suppressions File

For generating the supressions file feed the generated report into ground zero:

    $ groundzero checkstyle-warnings.xml

This will produce a suppressions file like this:

```
<?xml version="1.0" encoding"UTF-8"?>
<!DOCTYPE suppressions PUBLIC "-//Puppy Crawl//DTD Suppressions 1.1//EN"
                              "http://www.puppycrawl.com/dtds/suppressions_1_1.dtd">
<suppressions>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="15" columns="8" checks="UnusedImportsCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="25" columns="5" checks="JavadocVariableCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="26" columns="5" checks="JavadocVariableCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="28" columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="34" columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/BusinessServiceProvider\.java"
              lines="38" columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/UserService\.java" lines="34"
              columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/UserService\.java" lines="38"
              columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/registration/RegisrationUI\.java"
              lines="32" columns="8" checks="UnusedImportsCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/Registry\.java"
              lines="74" columns="5" checks="JavadocVariableCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/Registry\.java"
              lines="218" columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/Registry\.java"
              lines="222" columns="5" checks="JavadocMethodCheck"/>
    <suppress files="/home/weltraumschaf/foo/src/main/java/de/weltraumschaf/foo/resources/BaseResource\.java"
              lines="159" columns="5" checks="JavadocMethodCheck"/>
</suppressions>
```

[1]: http://checkstyle.sourceforge.net/
[2]: http://mxcl.github.io/homebrew/
[3]: http://www.maven.org/
[4]: http://checkstyle.sourceforge.net/config.html
