# Kscript network cli
This is a simple project for testing [kscript](https://github.com/holgerbrandl/kscript) and coroutines. 
The cli simply finds all network interfaces on host and attempts to ping each host on subnet with range /24. 
Each ping is launched as new coroutine asynchronously.

## Setting up annotation support in IDEA
Including screenshot since I had some troubles with this initially. 
"kscript-annotations" on the bottom is what is necessary, the rest are added within the script files themselves
or by kscript.

![idea project setup](/screenshots/idea_project_structure_annotation_setup.png)
