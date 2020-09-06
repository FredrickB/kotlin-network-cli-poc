# kotlin network cli proof of concept
This is a simple proof of concept for testing [kscript](https://github.com/holgerbrandl/kscript) and coroutines. 

Simply finds all network interfaces on the host and attempts to ping each host on subnet with range /24. 

_The networking portion of the code is a best-case effort due to the underlying implementation of the networking libraries. It works ok enough for prrof of concept, but don't use it for anything serious._

## Development
### Setup environment
Assuming you already have [sdkman](https://sdkman.io/) installed:

1. `sdk install kotlin`
1. `sdk install kscript`

### Running the project
`kscript App.kts` 

### Setting up IDEA
`kscript --idea App.kts`

#### Annotation support
Including screenshot since I had some troubles with this initially. 
"kscript-annotations" on the bottom is what is necessary, the rest are added within the script files themselves or by kscript.

![idea project setup](/screenshots/idea_project_structure_annotation_setup.png)
