# Kscript network cli
This is a simple project for testing [kscript](https://github.com/holgerbrandl/kscript) and coroutines. 
The cli simply finds all network interfaces on the host and attempts to ping each host on subnet with range /24. 
Each ping is launched as new coroutine asynchronously.

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
