# Usage

## Create Some Reports

In first place you need some reports. To get them you need [Checkstyle][1] of course.

### Install Checkstyle For Command Line

- On __Debian__ systems type: `apt-get install checkstyle`
- On __Mac OS X__ with [Homebrew][2] type: `brew install checkstyle`

## Create Reports On Command Line

Choose source code you desire to create reports from. In the examples here the
[GroundZero source][3] itself is used. Also you need a [Checkstyle configuration][4].
For this example the configuration for this project is used. For generating a report
get the source and type:

    $ cd ~/tmp
    $ git clone https://github.com/Weltraumschaf/GroundZero.git
    $ cd GroundZero
    $ checkstyle -c src/main/config/checkstyle-ruleset.xml -f xml -o checkstyle-warnings.xml -r src/main/java

[1]: http://checkstyle.sourceforge.net/
[2]: http://mxcl.github.io/homebrew/
[3]: https://github.com/Weltraumschaf/GroundZero
[4]: http://checkstyle.sourceforge.net/config.html
