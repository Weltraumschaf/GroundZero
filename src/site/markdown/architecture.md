# Architecture

The base architecture is as followed: The [Checkstyle][1] reports are parsed with a [SAX][2] based parser into 
a semantic model which represents the Checkstyle reports. These report models are then transformed 
by a generator into XML suppressions files.

<img alt="Big picture" src="uml/overview.png"/>

## Semantic Model

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam purus sapien, sagittis placerat nisl vel, 
semper volutpat lorem. Vivamus interdum placerat ultricies. Interdum et malesuada fames ac ante ipsum 
primis in faucibus. Phasellus suscipit risus lectus, at congue libero malesuada vitae. Duis eget consequat 
est. Donec ut orci placerat, congue turpis a, interdum metus. Pellentesque sit amet vulputate orci, et 
dapibus arcu. Integer aliquam enim sollicitudin pulvinar condimentum. Vivamus at convallis massa. 

<img alt="Big picture" src="uml/model.png"/>

[1]: http://checkstyle.sourceforge.net/
[2]: http://de.wikipedia.org/wiki/Simple_API_for_XML
