# Express Recommend
Express Recommend is a service that makes it easier for students and institutions to share and verify recommendation letters
## Development
To transcompile the Typescript and bundle all web assets run
```bash
cd frontend && npm run build
```
To run the frontend development server
```bash
cd frontend && npm run live-server
```

## Build
To build docker images for the backend service:
```bash
docker compose build backend
```
Same for frontend:
```bash
docker compose build frontend
```

## Running the application
Use the following commands to run
docker containers for frontend, 
backend, and the PostgreSQL database
```bash
docker compose up
```

## Contributing
Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License
Distributed under the MIT License. See [LICENSE](LICENSE) for more information.
