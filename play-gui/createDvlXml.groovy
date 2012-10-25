def id = 0
def factor = 0
def last = 0
def toFile = new File("output.xml")

new File("testfile.txt").eachLine {
    if(last >= 0) factor = 4
    if(last >= 16) factor = 2
    if(last >= 24) factor = 4
    if(last >= 64) factor = 2
    if(last >= 84) factor = 4

    toFile << """
parts.add(createImagePart(\"$id\", new Interval($last, $factor), new URL(\"$it\")));"""
    last = last + factor
    id++

}
/*
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
}*/