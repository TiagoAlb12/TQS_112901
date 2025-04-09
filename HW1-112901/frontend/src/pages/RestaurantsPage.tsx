import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./RestaurantsPage.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark, faPlus, faMagnifyingGlass, faTemperatureHigh, faTemperatureLow, faCloudRain } from "@fortawesome/free-solid-svg-icons";

interface Restaurant {
    id: number;
    name: string;
    location: string;
}

interface WeatherForecast {
    date: string;
    maxTemp: number;
    minTemp: number;
    precipitation: number;
}

interface Meal {
    date: string;
    description: string;
    type: "ALMOCO" | "JANTAR";
    forecast: WeatherForecast;
}

interface GroupedMeal {
    date: string;
    almoco?: Meal;
    jantar?: Meal;
}

interface Reservation {
    token: string;
    restaurantName: string;
    date: string;
    checkedIn: boolean;
    cancelled: boolean;
    type: "ALMOCO" | "JANTAR";
}

export function RestaurantsPage() {
    const [restaurants, setRestaurants] = useState<Restaurant[]>([]);
    const [selectedId, setSelectedId] = useState<number | null>(null);
    const [groupedMeals, setGroupedMeals] = useState<GroupedMeal[]>([]);
    const [selectedForecast, setSelectedForecast] = useState<WeatherForecast | null>(null);
    const [selectedDate, setSelectedDate] = useState<string | null>(null);
    const [showReservationOptions, setShowReservationOptions] = useState(false);
    const [showSuccessModal, setShowSuccessModal] = useState(false);
    const [reservationToken, setReservationToken] = useState<string | null>(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [showErrorModal, setShowErrorModal] = useState(false);


    const navigate = useNavigate();

    function handleReservation(type: "ALMOCO" | "JANTAR") {
        if (!selectedId || !selectedDate) return;
    
        const stored = localStorage.getItem("reservationTokens");
        const tokens: string[] = stored ? JSON.parse(stored) : [];
    
        Promise.allSettled(tokens.map(token =>
            fetch(`http://localhost:8081/api/reservations/${token}`)
                .then(res => {
                    if (!res.ok) throw new Error("Token inválido");
                    return res.json();
                })
        )).then(results => {
            const reservas = results
                .filter(r => r.status === "fulfilled")
                .map(r => (r as PromiseFulfilledResult<Reservation>).value);
    
            const conflito = reservas.find(r =>
                r.date === selectedDate &&
                r.restaurantName &&
                r.type === type &&
                !r.cancelled
            );
    
            if (conflito) {
                setErrorMessage(`Já existe uma reserva para ${type === "ALMOCO" ? "almoço" : "jantar"} nesta data.`);
                setShowErrorModal(true);
                setShowReservationOptions(false);
                return;
            }
    
            fetch("http://localhost:8081/api/reservations", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ restaurantId: selectedId, date: selectedDate, type })
            })
                .then(res => res.json())
                .then(data => {
                    const updatedTokens = [...tokens, data.token];
                    localStorage.setItem("reservationTokens", JSON.stringify(updatedTokens));
                    setReservationToken(data.token);
                    setShowSuccessModal(true);
                    setShowReservationOptions(false);
                })
                .catch(err => {
                    console.error("Erro ao criar reserva:", err);
                    alert("Ocorreu um erro ao criar a reserva.");
                });
        });
    }    

    useEffect(() => {
        fetch("http://localhost:8081/api/restaurants")
            .then((res) => res.json())
            .then((data) => setRestaurants(data))
            .catch((err) => console.error("Erro ao buscar restaurantes:", err));
    }, []);

    useEffect(() => {
        if (selectedId !== null) {
            fetch(`http://localhost:8081/api/meals?restaurantId=${selectedId}`)
                .then((res) => res.json())
                .then((data: Meal[]) => {
                    const grouped: Record<string, GroupedMeal> = {};

                    data.forEach((meal) => {
                        if (!grouped[meal.date]) {
                            grouped[meal.date] = { date: meal.date };
                        }
                        if (meal.type === "ALMOCO") grouped[meal.date].almoco = meal;
                        if (meal.type === "JANTAR") grouped[meal.date].jantar = meal;
                    });

                    setGroupedMeals(Object.values(grouped));
                });
        }
    }, [selectedId]);

    return (
        <div className="restaurant-page" style={{ paddingTop: "80px", paddingBottom: "60px" }}>
            <div className="page-header">
                <h2>Selecione a Cantina</h2>
                <div className="divider">
                    <button className="reservation-button" onClick={() => navigate("/reservas")}>
                        As minhas reservas
                    </button>
                </div>
            </div>
            <select onChange={(e) => setSelectedId(Number(e.target.value))}>
                <option value="">-- Escolha um restaurante --</option>
                {restaurants.map((r) => (
                    <option key={r.id} value={r.id}>
                        {r.name}
                    </option>
                ))}
            </select>

            {groupedMeals.length > 0 && (
                <div className="table-wrapper">
                    <table className="meals-table grouped">
                        <thead>
                            <tr>
                                <th>Data</th>
                                <th>Almoço</th>
                                <th>Jantar</th>
                                <th>Metereologia</th>
                                <th>Reservas</th>
                            </tr>
                        </thead>
                        <tbody>
                            {groupedMeals.map((group, i) => (
                                <tr key={i}>
                                    <td>{group.date}</td>
                                    <td>{group.almoco?.description || "-"}</td>
                                    <td>{group.jantar?.description || "-"}</td>
                                    <td>
                                        {(group.almoco?.forecast || group.jantar?.forecast) ? (
                                            <button className="weather-button" onClick={() => setSelectedForecast(group.almoco?.forecast || group.jantar?.forecast!)}>
                                                <FontAwesomeIcon icon={faMagnifyingGlass} /> Detalhes
                                            </button>
                                        ) : "--"}
                                    </td>
                                    <td>
                                        <button
                                            className="reservation-btn"
                                            onClick={() => {
                                                setSelectedDate(group.date);
                                                setShowReservationOptions(true);
                                            }}
                                        >
                                            <FontAwesomeIcon icon={faPlus} />
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {selectedForecast && (
                <div className="weather-modal">
                    <div className="weather-content">
                        <h3><b>Previsão para {selectedForecast.date}</b></h3>
                        <p><FontAwesomeIcon icon={faTemperatureHigh} /> <b>Máxima:</b> {selectedForecast.maxTemp}°C</p>
                        <p><FontAwesomeIcon icon={faTemperatureLow} /> <b>Mínima:</b> {selectedForecast.minTemp}°C</p>
                        <p><FontAwesomeIcon icon={faCloudRain} /> <b>Precipitação:</b> {selectedForecast.precipitation} mm</p>
                        <button className="weather-button" onClick={() => setSelectedForecast(null)}>Fechar</button>
                    </div>
                </div>
            )}

            {showReservationOptions && selectedDate && (
                <div className="reservation-modal">
                    <div className="reservation-content">
                        <h3>Fazer <b>reserva</b> para <b>{selectedDate}</b></h3>
                        <button className="reservation-btn" onClick={() => handleReservation("ALMOCO")}>Reservar Almoço</button>
                        <button className="reservation-btn" onClick={() => handleReservation("JANTAR")}>Reservar Jantar</button>
                        <button className="cancel-btn" onClick={() => setShowReservationOptions(false)}>
                            <FontAwesomeIcon icon={faXmark} /> Cancelar
                        </button>
                    </div>
                </div>
            )}

            {showSuccessModal && reservationToken && (
                <div className="reservation-modal">
                    <div className="reservation-content">
                        <h3><b>Reserva criada com sucesso!</b></h3>
                        <p>O teu token de reserva é:</p>
                        <p style={{ fontWeight: "bold", color: "#4CAF50" }}>{reservationToken}</p>
                        <button className="reservation-btn" onClick={() => setShowSuccessModal(false)}>
                            Fechar
                        </button>
                    </div>
                </div>
            )}

            {showErrorModal && (
                <div className="reservation-modal">
                    <div className="reservation-content">
                        <h3 style={{ color: "#d9534f" }}>Erro ao criar reserva</h3>
                        <p>{errorMessage}</p>
                        <button className="reservation-btn" onClick={() => setShowErrorModal(false)}>
                            Fechar
                        </button>
                    </div>
                </div>
            )}

        </div>
    );
}
