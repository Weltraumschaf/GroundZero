# Welcome to the Ground Zero Project

A tool to generate line based suppression files for [Checkstyle][1].

## What is Ground Zero

Most of you know [Checkstyle][1] a very useful code quality tool. But also most of you know these historical
grown software projects where a analysis with [Checkstyle][1] leads to hundreds of thousands warnings and errors.
Developers not experienced with a situation like this tend to say: "Ok, lets fix this shit." A few thousand
warnings later everybody is frustrated about the low progress in getting zero warnings. It's like a fight
against windmills.

A more practical approach is to use [Checkstyle][1], but suppress all warnings and errors from a beginning point
based on line and column of their occurrence. If a developer edit the sources so that the position of the
suppressed warning will change it pops up because it is no longer suppressed. In combination with a team
commitment that nothing with warnings may be committed the developer is forced to remove this few warnings.
This approach is less frustrating because you do not waste your effort. You clean up the parts of your software
where you do the work.

This tool helps you to generate a line based suppression file from a [Checkstyle][1] report. Add this suppression
file to your [Checkstyle][1] configuration and have fun fixing the warnings.

## Build and Install

A current [Maven][2] installation is required.

Clone this repository and change into in:

    $ git clone https://github.com/Weltraumschaf/GroundZero.git
    $ cd GroundZero

Build with maven:

    $ mvn clean install

Have fun:

    $ ./bin/groundzero -h

[1]: http://checkstyle.sourceforge.net/
[2]: http://maven.apache.org/
