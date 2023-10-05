Feature: ApiSearch & LookUp

  @test
  Scenario: User verifies if iTunes searchUrl is secure to navigate
    Given user looks for searchUrl to navigates to itunes
    Then verify searchURL contains https and secure url to navigate

  @test
  Scenario Outline: User tries to search iTunes by 'content' and verify if response is received
    Given user navigates to itunes
    When user enters "<term>" for a search
    Then search results should not be empty
    And search results "<validate_results>" contains values "<for_values>"
    Examples:
      | term| validate_results |for_values|
      | jack+johnson| all      |Jack Johnson|
      | jack+johnson&limit=25| limit| 25|
      | jack+johnson&entity=musicVideo| kind| music-video|
      | jim+jones&country=ca| country       | CA         |
      | yelp&country=us&entity=software|wrapperType|software|

  @test
  Scenario Outline: User tries to search iTunes by 'content' and verify search results displays mandatory details
    Given user navigates to itunes
    When user enters "<term>" for a search and validate schema
    Examples:
      | term|
      | jack+johnson|
      | jack+johnson&limit=25|
      | jack+johnson&entity=musicVideo|
      | jim+jones&country=ca|
      | yelp&country=us&entity=software|

  @test
  Scenario Outline: ResponseTime for search iTunes by 'content' and verify if response time is less than 2seconds
    Given user navigates to itunes
    Then verify response time for "<term>" search less than 2seconds
    Examples:
      | term|
      | jack+johnson|
      | jack+johnson&limit=25|
      | jack+johnson&entity=musicVideo|
      | jim+jones&country=ca|
      | yelp&country=us&entity=software|

  @test
  Scenario Outline: User tries to 'lookup' iTunes by 'ID'
  and verify if response is received
    Given user navigates to itunes
    When user enters "<lookUp>" for a Lookup
    Then search results should not be empty
    And lookup results "<validate_results>" contains values "<for_values>"
    Examples:
      | lookUp| validate_results |for_values|
      | id=909253|artistName| Jack Johnson|
      | id=909253&entity=album| collectionType| Album|
      | id=284910350| kind| software|
      | id=284910350| wrapperType | software|
      | id=284910350| artistName| Yelp|
      | id=284910350| sellerName| Yelp|
      | id=284910350| trackName | Yelp|

  @test
  Scenario Outline: User tries to 'lookup' iTunes by amgArtistId
  and verify if response is received
    Given user navigates to itunes
    When user enters "<lookUp>" for a Lookup
    Then search results should not be empty
    And lookup results "<validate_results>" contains values "<for_values>"
    Examples:
      | lookUp| validate_results| for_values|
      | amgArtistId=468749| artistName| Jack Johnson|
      | amgArtistId=468749,5723| artistName| multiple artist|
      | amgArtistId=468749,5723&entity=album&limit=5| artistName| multiple artist|
      | amgArtistId=468749,5723&entity=album&limit=5| collectionType| Album|
      | amgArtistId=468749,5723&entity=album&limit=5| limit| 5|
      | amgArtistId=468749,5723&entity=song&limit=5&sort=recent | kind| song|
      | amgArtistId=468749,5723&entity=song&limit=5&sort=recent | limit| 5|

  @test
  Scenario Outline: User tries to 'lookup' iTunes by Id,UPC,AMG_albumID
  and verify if response is received
    Given user navigates to itunes
    When user enters "<lookUp>" for a Lookup
    Then search results should not be empty
    And lookup results "<validate_results>" contains values "<for_values>"
    Examples:
      | lookUp| validate_results| for_values|
      | upc=720642462928| collectionType| Album|
      | upc=720642462928&entity=song| kind| song|
      | amgAlbumId=15175,15176,15177,15178,15183,15184,15187,1519,15191,15195,15197,15198| collectionType| Album|


  @test
  Scenario Outline: User tries to 'lookup' iTunes by 'AMG_videoID,'ISBN'
  and verify if response is received
    Given user navigates to itunes
    When user enters "<lookUp>" for a Lookup
    Then search results should not be empty
    And lookup results "<validate_results>" contains values "<for_values>"
    Examples:
      | lookUp| validate_results| for_values|
      | amgVideoId=17120| kind| video|
      | isbn=9780316069359| kind| book|

  @test
  Scenario Outline: ResponseTime for 'lookup' iTunes by 'ID','ArtistID,'UPC','AlbumID','videosID','ISBN'
  and verify if response is received
    Given user navigates to itunes
    Then verify response time for "<lookUp>" search less than 2seconds
    Examples:
      | lookUp|
      | id=909253|
      | id=284910350|
      | amgArtistId=468749|
      | amgArtistId=468749,5723|
      | id=909253&entity=album|
      | amgArtistId=468749,5723&entity=album&limit=5|
      | amgArtistId=468749,5723&entity=song&limit=5&sort=recent|
      | upc=720642462928|
      | upc=720642462928&entity=song|
      | amgAlbumId=15175,15176,15177,15178,15183,15184,15187,1519,15191,15195,15197,15198|
      | amgVideoId=17120|
      | isbn=9780316069359|