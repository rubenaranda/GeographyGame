interface ScoreRepository {
    fun list() : List<Score>
    fun insert(score: Score)
}