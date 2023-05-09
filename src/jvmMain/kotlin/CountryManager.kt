import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FileCountryRepository : CountryRepository{
    val json = CountryData::class.java.getResource("/countries.json")!!.readText(Charsets.UTF_8)
    private val countries = Json { ignoreUnknownKeys = true }.decodeFromString<List<CountryData>>(json)

    override fun list(): List<CountryData> = countries.toList()
}

object ServiceLocator {
    val countryRepository : CountryRepository = FileCountryRepository()
}

