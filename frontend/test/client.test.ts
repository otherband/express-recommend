import { postCreateLetterRequest } from "../src/main"

test(
    "Create letter",  async () => {
        await postCreateLetterRequest("yazan", "yazan amer")
    }
)