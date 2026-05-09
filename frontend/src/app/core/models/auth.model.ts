export interface AuthLoginRequest {
  username: string;
  password: string;
}

export interface AuthCreateUserRequest {
  username: string;
  password: string;
  name: string;
  surname: string;
  email: string;
  birthDay: string;
  roleRequest: AuthCreateRoleRequest;
}

export interface AuthCreateRoleRequest {
  roleListName: string[];
}

export interface AuthResponse {
  username: string;
  message: string;
  jwt: string;
  status: boolean;
}

export interface User {
  id: number;
  username: string;
  name: string;
  surname: string;
  email: string;
  birthDay: string;
  createdAt: string;
  roles: string[];
}
