const http = require('http');
const path = require('path');
const handler = require('serve-handler');

const port = process.env.PORT || 4200;
const publicDir = path.join(__dirname, 'dist', 'frontend', 'browser');

const server = http.createServer((request, response) => {
  return handler(request, response, {
    public: publicDir,
    cleanUrls: true,
    rewrites: [
      { source: '**', destination: '/index.html' }
    ]
  });
});

server.listen(port, () => {
  console.log(`Frontend listening on port ${port}`);
});
