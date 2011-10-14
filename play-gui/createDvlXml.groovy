def id = 0
def factor = 4

new File("testfile.txt").eachLine {
        println """<part>
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