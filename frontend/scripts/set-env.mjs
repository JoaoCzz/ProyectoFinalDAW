import { mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';

const apiUrl = process.env.NG_APP_API_URL || '/api';
const filePath = path.resolve('src/environments/environment.prod.ts');

const contents = `export const environment = {
  production: true,
  apiUrl: '${apiUrl}'
};
`;

await mkdir(path.dirname(filePath), { recursive: true });
await writeFile(filePath, contents, 'utf8');
