
class FileScoreRepository : ScoreRepository{
    private val scores = mutableListOf<Score>()

    override fun list(): List<Score> = scores.toList()

    override fun insert(score: Score) {
        scores.add(score)
    }
}

object ServiceScoreLocator {
    val scoreRepository : ScoreRepository = FileScoreRepository()
}
