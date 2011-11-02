def id = 0
def factor = 4
def toFile = new File("output.txt")
new File("play-gui/testfile.txt").eachLine {
        toFile << """<part>
  <id>cfw$id</id>
  <dvl>
    <url>$it</url>
    <name>Click Flash Wirr</name>
  </dvl>  
  <start>${id*factor}</start>  
  <end>${id*factor+factor}</end>
</part>"""
  id++
}