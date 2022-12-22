object Day07 extends Problem[Long, Long]:
  def solve(input: String) =
    given dirMap: Map[String, Entity] = buildDirMap(getLines(input))
    val smallDirs = dirMap.collect {
      case (_, a: Dir) if a.size < 100000 => a.size
    }
    (smallDirs.sum, directoriesToDelete)

  def directoriesToDelete(using entities: Map[String, Entity]) =
    val freeSpace = 70000000 - entities("/").size
    val neededSpace = 30000000 - freeSpace
    entities.collect {
      case (_, dir: Dir) if dir.size >= neededSpace => dir.size
    }.min

  def buildDirMap(lines: Seq[String]): Map[String, Entity] =
    lines
      .foldLeft((List.empty[String], Map[String, Entity]("/" -> Dir()))) {
        case ((pathFromHome, acc), line) =>
          line.split(" ") match {
            case Array("$", "cd", "..")    => (pathFromHome.tail, acc)
            case Array("$", "cd", nextDir) => (nextDir :: pathFromHome, acc)
            case Array("$", "ls")          => (pathFromHome, acc)
            case Array("dir", name) =>
              (pathFromHome, updateMapWithEntity(acc, name, pathFromHome, None))
            case Array(size, name) =>
              (
                pathFromHome,
                updateMapWithEntity(acc, name, pathFromHome, Some(size.toLong))
              )
          }
      }
      ._2

  def updateMapWithEntity(
      map: Map[String, Entity],
      entName: String,
      path: List[String],
      size: Option[Long]
  ) =
    val fullName = (entName :: path).mkString
    val ent = size.fold(Dir())(File(_))
    val currDir = map(path.mkString).asInstanceOf[Dir]
    map
      .updated(fullName, ent)
      .updated(path.mkString, currDir.addChild(fullName))

  sealed trait Entity:
    def children: Seq[String]
    def size(using m: Map[String, Entity]): Long

  case class File(size: Long) extends Entity:
    val children = Nil
    def size(using m: Map[String, Entity]) = size

  case class Dir(children: Seq[String] = Seq.empty) extends Entity:
    def size(using m: Map[String, Entity]) = children.map(m(_).size).sum
    def addChild(c: String) = this.copy(children = children :+ c)
