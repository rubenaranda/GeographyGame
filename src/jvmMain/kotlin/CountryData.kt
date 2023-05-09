import kotlinx.serialization.Serializable

@Serializable
data class CountryData(val name: CountryName, val capital: List<String> = listOf(), val flag: String)

@Serializable
data class CountryName(val common: String)