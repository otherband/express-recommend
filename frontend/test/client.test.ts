import { postCreateLetterRequest } from "../src/main"

test(
    "Create letter",  async () => {
        const response  = await postCreateLetterRequest("yazan@yaz.com", "yazan amer")
        expect(response.status).toBe(201);
    }
)