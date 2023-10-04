const API_BASE = "http://localhost:8085/api/v1";
const RECOMMENDATION_URL = API_BASE.concat("/recommendation-letter");
const TOKEN_URL = API_BASE.concat("/token");
const CREATE_LETTER_FORM = "create-letter-form";
const CREATE_LETTER_RESULT_DIV = "create-letter-result-div";

async function submitCreateLetterForm() {
  const form = document.getElementById(CREATE_LETTER_FORM) as HTMLFormElement;
  const formData = new FormData(form);
  if (form.checkValidity()) {
    postCreateLetterRequest(
      formData.get("letterAuthorEmail").toString(),
      formData.get("letterBody").toString()
    )
      .then(async (response) => {
        if (response.status == 201) {
          showResult(
            "Letter created successfully. Please check your email for the verification link"
          );
        } else {
            const responseText = await response.text();
            showResult("Failed to create letter " + responseText);
        }
      })
      .catch((exception) => {
        console.log(exception);
        showResult("Something went wrong...");
      });
  } else {
    form.reportValidity();
  }
}

function showResult(message: string): void {
  document
    .getElementById(CREATE_LETTER_FORM)
    .setAttribute("style", "display: none");
  document
    .getElementById(CREATE_LETTER_RESULT_DIV)
    .setAttribute("style", "display: block");
  document.getElementById("create-letter-result-message-div").textContent =
    message;
}

function postCreateLetterRequest(
  letterAuthorEmail: string,
  letterBody: string
): Promise<Response> {
  return fetch(RECOMMENDATION_URL, {
    headers: {
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      authorEmail: letterAuthorEmail,
      body: letterBody,
    }),
  });
}

export { postCreateLetterRequest, submitCreateLetterForm };
