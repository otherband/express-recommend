const API_BASE = "http://localhost:8085/api/v1"
const RECOMMENDATION_URL = API_BASE.concat("/recommendation-letter")
const TOKEN_URL = API_BASE.concat("/token")



function postCreateLetterRequest(letterAuthorEmail: string, letterBody: string): Promise<Response> {
    return fetch(
        RECOMMENDATION_URL, 
        {
            "method": "POST",
            "body": JSON.stringify({
                "authorEmail": letterAuthorEmail,
                "body": letterBody
            })
        }
    )
}

export {postCreateLetterRequest}