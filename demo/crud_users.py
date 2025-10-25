import requests
import random
import json

BASE_URL = "http://localhost:8080/api/users"

# data for random operations
names = ["Ana", "Luis", "Carlos", "María", "Benjamín", "Sofía"]
last_names = ["García", "López", "Martínez", "Cisneros", "Barraza", "Hernández"]
titles = ["Ingeniero de Software", "Analista de Datos", "Diseñador UX", "Scrum Master"]
countries = ["México", "Argentina", "Chile", "Colombia", "España"]
practices = ["Desarrollo", "Calidad", "Diseño", "Gestión", "Soporte"]


def print_response(response, label = "Respuesta: "):
    print(f"\n{label}\n")
    print(json.dumps(response.json(), indent=4, ensure_ascii=False))
    print(f"*******************************************************\n")

def get_user_list():
    response = requests.get(BASE_URL)
    print_response(response, "Usuarios:")

def create_new_user():
    payload = {
        "email": random_email(),
        "password": random_password(),
        "firstName": random_first_name(),
        "lastName": random_last_name(),
        "title": random_title(),
        "country": random_country(),
        "practice": random_practice(),
        "lanId": random_lan_id()
    }

    response = requests.post(BASE_URL, json=payload)
    print_response(response, "Usuario creado:")

def random_lan_id():
    return f"lan{random.randint(1000,9999)}"

def random_practice():
    return random.choice(practices)

def random_country():
    return random.choice(countries)

def random_title():
    return random.choice(titles)

def random_last_name():
    return random.choice(last_names)

def random_first_name():
    return random.choice(names)

def random_password():
    return f"Pass{random.randint(1000,9999)}!"

def random_email():
    return f"user{random.randint(1000,9999)}@ejemplo.com"

def show_user_by_id():
    user_id = input("Ingrese el ID del usuario: ")
    response = requests.get(f"{BASE_URL}/{user_id}")
    print_response(response, "Usuario:")

def editar_usuario():
    user_id = input("Ingrese el ID del usuario a editar: ")
    payload = {
        "firstName": f"{random_first_name()}_PUT_UPDATE_FROM_SCRIPT",
        "lastName": f"{random_last_name()}",
        "email": f"{random_lan_id()}@ejemplo_put_method.com",
        "title": f"{random_title()}",
        "country": random_country(),
        "practice": random_practice(),
        "lanId": random_lan_id()
    }
    response = requests.put(f"{BASE_URL}/{user_id}", json=payload)
    print_response(response, "Usuario actualizado:")

def patch_lan_id():
    user_id = input("Ingrese el ID del usuario para editar LAN Id: ")
    new_lan_id = input("Nuevo LAN Id: ")
    response = requests.patch(f"{BASE_URL}/{user_id}", json={"lanId": new_lan_id})
    print_response(response, "LAN Id actualizado:")

def delete_user():
    user_id = input("Ingrese el ID del usuario a borrar: ")
    response = requests.delete(f"{BASE_URL}/{user_id}")
    if response.status_code == 200:
        print("Usuario eliminado correctamente.")
    else:
        print("Error al eliminar usuario.")
    print_response(response)

menu_options = [
    {"label": "Listar usuarios", "action": get_user_list},
    {"label": "Ver usuario por ID", "action": show_user_by_id},
    {"label": "Crear usuario (valores aleatorios)", "action": create_new_user},
    {"label": "Editar usuario (valores aleatorios)", "action": editar_usuario},
    {"label": "Editar lanId (PATCH)", "action": patch_lan_id},
    {"label": "Eliminar usuario", "action": delete_user},
    {"label": "Salir", "action": None}
]

def menu():
    while True:
        print("========================")
        print("Seleccione una opción:")
        for i, option in enumerate(menu_options, start=1):
            print(f"{i}. {option['label']}")
        print("========================")
        print("")
        choice = input("Opción: ")
        

        if choice.isdigit():
            index = int(choice) - 1
            if 0 <= index < len(menu_options):
                selected = menu_options[index]
                if selected["action"]:
                    selected["action"]()
                else:
                    print("¡Gracias por usar el sistema! Hasta pronto.")
                    break
            else:
                print("Opción fuera de rango.")
        else:
            print("Entrada inválida. Por favor ingrese un número.")


if __name__ == "__main__":
    menu()
