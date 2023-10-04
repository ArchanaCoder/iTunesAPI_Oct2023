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
    And search results contains text "<term>"
    Examples:
      | term |
      | jack+johnson |
      | jack+johnson&limit=25 |
      | jack+johnson&entity=musicVideo |
      | jim+jones&country=ca           |
      | yelp&country=us&entity=software |

  @test
  Scenario Outline: User tries to search iTunes by 'content' and verify search results displays mandatory details
    Given user navigates to itunes
    When user enters "<term>" for a search and validate schema
    Examples:
      | term |
      | jack+johnson |
      | jack+johnson&limit=25 |
      | jack+johnson&entity=musicVideo |
      | jim+jones&country=ca           |
      | yelp&country=us&entity=software |

#  @test2
#  Scenario Outline: User tries to search iTunes by 'content' and verify if search results provides links to navigate
#    Given user navigates to itunes
#    When user enters "<term>" for a search
#    Then verify viewURLs are clickable
#    Examples:
#      | term |
#      | jack+johnson |
#      | jack+johnson&limit=25 |
#      | jack+johnson&entity=musicVideo |
#      | jim+jones&country=ca           |
#      | yelp&country=us&entity=software |

  @test
  Scenario Outline: User tries to search iTunes by 'content' and verify if response time is less than 2seconds
    Given user navigates to itunes
    Then verify response time for "<term>" search less than 2seconds
    Examples:
      | term |
      | jack+johnson |
      | jack+johnson&limit=25 |
      | jack+johnson&entity=musicVideo |
      | jim+jones&country=ca           |
      | yelp&country=us&entity=software |

  @test2
  Scenario Outline: User tries to 'lookup' iTunes by 'ID','ArtistID,'UPC','AlbumID','videosID','ISBN'
  and verify if response is received
    Given user navigates to itunes
    When user enters "<lookUp>" for a Lookup
    Then search results should not be empty
    And lookup results contains values "<lookUp>"
    Examples:
      | lookUp |
      | id=909253 |
      | id=284910350 |
      | amgArtistId=468749 |
      | amgArtistId=468749,5723 |
      | id=909253&entity=album |
      | amgArtistId=468749,5723&entity=album&limit=5 |
      | amgArtistId=468749,5723&entity=song&limit=5&sort=recent |
      | upc=720642462928 |
      | upc=720642462928&entity=song |
      | amgAlbumId=15175,15176,15177,15178,15183,15184,15187,1519,15191,15195,15197,15198 |
      | amgVideoId=17120 |
      | isbn=9780316069359 |

  @test
  Scenario Outline: User tries to 'lookup' iTunes by 'ID','ArtistID,'UPC','AlbumID','videosID','ISBN'
  and verify if response is received
    Given user navigates to itunes
    Then verify response time for "<lookUp>" search less than 2seconds
    Examples:
      | lookUp |
      | id=909253 |
      | id=284910350 |
      | amgArtistId=468749 |
      | amgArtistId=468749,5723 |
      | id=909253&entity=album |
      | amgArtistId=468749,5723&entity=album&limit=5 |
      | amgArtistId=468749,5723&entity=song&limit=5&sort=recent |
      | upc=720642462928 |
      | upc=720642462928&entity=song |
      | amgAlbumId=15175,15176,15177,15178,15183,15184,15187,1519,15191,15195,15197,15198 |
      | amgVideoId=17120 |
      | isbn=9780316069359 |